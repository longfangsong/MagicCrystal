package com.fairchild_superconductor.magic_crystal.electric

import com.fairchild_superconductor.magic_crystal.electric.battery.BatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.battery.debug.DebugBatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.machine.MachineEntity
import com.fairchild_superconductor.magic_crystal.electric.machine.debug.DebugMachineEntity
import com.fairchild_superconductor.magic_crystal.electric.wire.WireEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import org.ejml.data.DMatrixSparseCSC
import org.ejml.sparse.FillReducing
import org.ejml.sparse.csc.factory.LinearSolverFactory_DSCC
import kotlin.test.Test


data class Solution(
    // note: for batteries, its potential doesn't have to be calculated
    // for machines, its potential is fixed to 0
    val potential: Map<WireEntity, Double>,
    val currents: Map<Current, Double>
) {
    override fun toString(): String {
        return "potential:\n" + potential.map { "  ${it.key.pos}: ${it.value}V" }.joinToString(",\n") +
                "\ncurrent:\n" + currents.map { "  ${it.key}: ${it.value}A" }.joinToString(",\n")
    }
}

fun solve(
    endpoints: List<ElectricBlockEntity>,
    currents: List<Current>
): Solution {
    val resultPotentials = mutableMapOf<WireEntity, Double>()
    val resultCurrents = mutableMapOf<Current, Double>()

    // for currents which connect directly from a battery to a machine, we can calculate the current value directly
    val (decideDirectlyCurrents, remainCurrents) = currents.partition { it.start is BatteryEntity && it.end is MachineEntity }
    for (decideDirectlyCurrent in decideDirectlyCurrents) {
        resultCurrents[decideDirectlyCurrent] =
            (decideDirectlyCurrent.start as BatteryEntity).potential / decideDirectlyCurrent.resistance
    }

    val wireEndPoints = endpoints.filterIsInstance<WireEntity>()
    val size = wireEndPoints.size + remainCurrents.size
    // X[0..wireEndPoints.size] is potential of each wire endpoint, ie. X[i] = wireEndPointPotential[i]
    // X[wireEndPoints.size..] is the current value on each branch, ie. X[i] = currentValue[i-wireEndPoints.size]
    val a = DMatrixSparseCSC(size, size, size * 6)
    val b = DMatrixSparseCSC(size, 1, endpoints.filterIsInstance<BatteryEntity>().size)

    var currentEquationIndex = 0

    // pressure drop on one certain current is its currentValue * resistance
    // so currentEndPotential + currentValue * resistance - currentStartPotential = 0
    for (currentWithIndex in remainCurrents.withIndex()) {
        val currentIndex = currentWithIndex.index + wireEndPoints.size
        val current = currentWithIndex.value
        if (current.start is BatteryEntity) {
            // todo: battery to battery connection
            // currentStartPotential is an constant
            // ie. currentEndPotential + currentValue * resistance = Constant
            val currentEndIndex = wireEndPoints.indexOf(current.end)
            a[currentEquationIndex, currentEndIndex] = 1.0
            a[currentEquationIndex, currentIndex] = current.resistance
            b[currentEquationIndex, 0] = (current.start as BatteryEntity).potential
            ++currentEquationIndex
        } else if (current.end is MachineEntity) {
            // currentEndPotential is 0
            // ie. currentValue * resistance - currentStartPotential = 0
            val currentStartIndex = wireEndPoints.indexOf(current.start)
            a[currentEquationIndex, currentStartIndex] = -1.0
            a[currentEquationIndex, currentIndex] = current.resistance
            ++currentEquationIndex
        } else {
            // normal case
            val currentStartIndex = wireEndPoints.indexOf(current.start)
            val currentEndIndex = wireEndPoints.indexOf(current.end)
            a[currentEquationIndex, currentStartIndex] = -1.0
            a[currentEquationIndex, currentEndIndex] = 1.0
            a[currentEquationIndex, currentIndex] = current.resistance
            ++currentEquationIndex
        }
    }

    // for each wireEndPoints, sum(currentIn) - sum(currentOut) = 0
    for (wireEntity in wireEndPoints) {
        for (currentInIndex in remainCurrents.withIndex().filter { it.value.end == wireEntity }
            .map { it.index + wireEndPoints.size }) {
            a[currentEquationIndex, currentInIndex] = 1.0
        }
        for (currentOutIndex in remainCurrents.withIndex().filter { it.value.start == wireEntity }
            .map { it.index + wireEndPoints.size }) {
            a[currentEquationIndex, currentOutIndex] = -1.0
        }
        ++currentEquationIndex
    }

    val solver = LinearSolverFactory_DSCC.lu(FillReducing.NONE)
    solver.setA(a)
    val x = DMatrixSparseCSC(1, size, size)
    a.print()
    b.print()
    solver.solveSparse(b, x)
    for (i in wireEndPoints.indices) {
        resultPotentials[wireEndPoints[i]] = x[i, 0]
    }
    for (i in remainCurrents.indices) {
        resultCurrents[remainCurrents[i]] = x[i + wireEndPoints.size, 0]
    }
    return Solution(resultPotentials, resultCurrents)
}

class ElectricNet(val solution: Solution) {
    companion object {
        fun fromBFS(blockView: BlockView, foundEntity: ElectricBlockEntity): ElectricNet? {
            return if (isCurrentEnd(blockView, foundEntity.pos)) {
                val pendingVisitDirections = mutableMapOf<BlockPos, MutableList<Direction>>()
                val pendingVisit = mutableListOf(foundEntity.pos)
                val visited = mutableSetOf<BlockPos>()
                val currents = mutableListOf<Current>()
                val endpoints = mutableSetOf<ElectricBlockEntity>()
                while (pendingVisit.isNotEmpty()) {
                    val toVisit = pendingVisit.removeFirst()
                    if (!visited.contains(toVisit)) {
                        val foundCurrentPoses = findCurrentsStartFrom(blockView, toVisit, pendingVisitDirections)
                        for (foundCurrentPos in foundCurrentPoses) {
                            val current =
                                Current(foundCurrentPos.map { blockView.getBlockEntity(it) as ElectricBlockEntity })
                            currents += current
                            endpoints += current.start
                            endpoints += current.end
                            pendingVisit += current.end.pos
                        }
                    }
                }
                return ElectricNet(solve(endpoints.toList(), currents))
            } else {
                null
            }
        }
    }
}

class TestE {
    @Test
    fun net() {
        val stubBlockView = ElectricTest.StubBlockView(
            listOf(
                DebugBatteryEntity(BlockPos(0, 0, 0), null, 10.0),
                WireEntity(BlockPos(0, 0, 1), null, 0.02),
                WireEntity(BlockPos(0, 0, 2), null, 0.02),
                WireEntity(BlockPos(0, 0, 3), null, 0.02),
                DebugMachineEntity(BlockPos(0, 0, 4), null, 10.0),
                WireEntity(BlockPos(1, 0, 1), null, 0.02),
                WireEntity(BlockPos(1, 0, 2), null, 0.02),
                WireEntity(BlockPos(1, 0, 3), null, 0.02),
                DebugMachineEntity(BlockPos(2, 0, 3), null, 10.0),
            )
        )
        val result = ElectricNet.fromBFS(stubBlockView, stubBlockView.getBlockEntity(BlockPos(0, 0, 0))!!)
        println(result!!.solution)
    }
}