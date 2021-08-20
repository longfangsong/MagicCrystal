package com.fairchild_superconductor.magic_crystal.electric

import com.fairchild_superconductor.magic_crystal.electric.battery.BatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.machine.MachineEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

private fun allDirectionsToVisit(blockView: BlockView, pos: BlockPos): List<Direction> {
    val result = mutableListOf<Direction>()
    for (face in Direction.values()) {
        if (blockView.getBlockEntity(pos + face) is ElectricBlockEntity) {
            result.add(face)
        }
    }
    return result
}

fun isCurrentEnd(blockView: BlockView, pos: BlockPos): Boolean {
    val entity = blockView.getBlockEntity(pos)
    return entity is BatteryEntity || entity is MachineEntity || allDirectionsToVisit(blockView, pos).size > 2
}

private fun seekOneCurrent(
    blockView: BlockView,
    startPos: BlockPos,
    pendingVisitDirections: MutableMap<BlockPos, MutableList<Direction>>
): List<BlockPos>? {
    val result = listOf(startPos)
    if (isCurrentEnd(blockView, startPos)) {
        return result
    }
    val nextVisitDirection = pendingVisitDirections[startPos]!!.removeFirstOrNull() ?: return null
    val nextVisitPos = startPos + nextVisitDirection
    if (pendingVisitDirections[nextVisitPos] == null) {
        pendingVisitDirections[nextVisitPos] = allDirectionsToVisit(blockView, nextVisitPos).toMutableList()
    }
    pendingVisitDirections[nextVisitPos]!!.remove(nextVisitDirection.reverse)
    val subResult = seekOneCurrent(blockView, nextVisitPos, pendingVisitDirections) ?: return null
    return result + subResult
}

/**
 * Find all currents starts from [startPos] in [blockView]
 * @return A list contains all currents found
 */
fun findCurrentsStartFrom(
    blockView: BlockView,
    startPos: BlockPos,
    pendingVisitDirections: MutableMap<BlockPos, MutableList<Direction>>
): List<List<BlockPos>> {
    assert(isCurrentEnd(blockView, startPos))
    val result = mutableListOf<List<BlockPos>>()
    if (pendingVisitDirections[startPos] == null)
        pendingVisitDirections[startPos] = allDirectionsToVisit(blockView, startPos).toMutableList()
    var nextVisitDirection = pendingVisitDirections[startPos]!!.removeFirstOrNull()
    while (nextVisitDirection != null) {
        val nextVisitPos = startPos + nextVisitDirection
        if (pendingVisitDirections[nextVisitPos] == null) {
            pendingVisitDirections[nextVisitPos] = allDirectionsToVisit(blockView, nextVisitPos).toMutableList()
        }
        pendingVisitDirections[nextVisitPos]!!.remove(nextVisitDirection.reverse)
        val subResult = seekOneCurrent(blockView, nextVisitPos, pendingVisitDirections)
        if (subResult != null) {
            result.add(listOf(startPos) + subResult)
        }
        nextVisitDirection = pendingVisitDirections[startPos]!!.removeFirstOrNull()
    }
    return result
}
