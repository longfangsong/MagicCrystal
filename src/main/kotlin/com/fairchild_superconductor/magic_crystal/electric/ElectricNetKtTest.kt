package com.fairchild_superconductor.magic_crystal.electric

import com.fairchild_superconductor.magic_crystal.electric.battery.debug.DebugBatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.machine.debug.DebugMachineEntity
import com.fairchild_superconductor.magic_crystal.electric.wire.WireEntity
import net.minecraft.util.math.BlockPos
import kotlin.test.Test

internal class ElectricNetKtTest {
    @Test
    fun solve() {
        val battery = DebugBatteryEntity(BlockPos(0, 0, 0), null, 10.0)
        val wires = listOf(
            WireEntity(BlockPos(0, 0, 1), null, 0.02),
            WireEntity(BlockPos(0, 0, 2), null, 0.02),
            WireEntity(BlockPos(0, 0, 3), null, 0.02),

            WireEntity(BlockPos(1, 0, 1), null, 0.02),
            WireEntity(BlockPos(1, 0, 2), null, 0.02),
            WireEntity(BlockPos(1, 0, 3), null, 0.02),
        )
        val machines = listOf(
            DebugMachineEntity(BlockPos(0, 0, 4), null, 10.0),
            DebugMachineEntity(BlockPos(2, 0, 3), null, 10.0)
        )
        val endpoints = listOf<ElectricBlockEntity>(battery) + machines +
                wires[0] + wires[1] + wires[2] + wires[4] + wires[5]
        val currents = listOf(
            Current(listOf(battery, wires[0])),
            Current(listOf(wires[0], wires[1])),
            Current(listOf(wires[1], wires[2])),
            Current(listOf(wires[2], machines[0])),
            Current(listOf(wires[0], wires[3], wires[4])),
            Current(listOf(wires[1], wires[4])),
            Current(listOf(wires[4], wires[5])),
            Current(listOf(wires[5], machines[1])),
        )
        val result = solve(endpoints, currents)
        println(result)
    }
}