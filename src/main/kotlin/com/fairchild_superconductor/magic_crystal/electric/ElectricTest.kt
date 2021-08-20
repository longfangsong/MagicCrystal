package com.fairchild_superconductor.magic_crystal.electric

import com.fairchild_superconductor.magic_crystal.electric.battery.debug.DebugBatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.machine.debug.DebugMachineEntity
import com.fairchild_superconductor.magic_crystal.electric.wire.WireEntity
import net.minecraft.block.BlockState
import net.minecraft.fluid.FluidState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import kotlin.test.Test

internal class ElectricTest {
    class StubBlockView(
        blockEntities: Iterable<ElectricBlockEntity>
    ) : BlockView {
        private var blockEntities = blockEntities.toList()
        override fun getHeight(): Int {
            return 128
        }

        override fun getBottomY(): Int {
            return 0
        }

        override fun getBlockEntity(pos: BlockPos?): ElectricBlockEntity? {
            return blockEntities.find { it.pos == pos }
        }

        override fun getBlockState(pos: BlockPos?): BlockState {
            TODO("Not yet implemented")
        }

        override fun getFluidState(pos: BlockPos?): FluidState {
            TODO("Not yet implemented")
        }

    }

    //   0 1 2 3 4
    // 0 o - -
    // 1     |
    // 2     + - x
    // 3     |
    // 4   - -
    private val stubBlockView = StubBlockView(
        listOf(
            DebugBatteryEntity(BlockPos(0, 0, 0), null,0.0),
            WireEntity(BlockPos(0, 0, 1), null, 0.0),
            WireEntity(BlockPos(0, 0, 2), null, 0.0),
            WireEntity(BlockPos(1, 0, 2), null, 0.0),
            WireEntity(BlockPos(2, 0, 2), null, 0.0),
            WireEntity(BlockPos(2, 0, 3), null, 0.0),
            WireEntity(BlockPos(3, 0, 2), null, 0.0),
            WireEntity(BlockPos(4, 0, 2), null, 0.0),
            WireEntity(BlockPos(4, 0, 1), null, 0.0),
            DebugMachineEntity(BlockPos(2, 0, 4), null, 0.0),
        )
    )

    //   0 1 2
    // 0 + + +
    // 1 + + +
    // 2 + + +
    private val stubBlockViewCycle = StubBlockView(
        listOf(
            WireEntity(BlockPos(0, 0, 0), null, 0.0),
            WireEntity(BlockPos(0, 0, 1), null, 0.0),
            WireEntity(BlockPos(0, 0, 2), null, 0.0),
            WireEntity(BlockPos(1, 0, 0), null, 0.0),
            WireEntity(BlockPos(1, 0, 1), null, 0.0),
            WireEntity(BlockPos(1, 0, 2), null, 0.0),
            WireEntity(BlockPos(2, 0, 0), null, 0.0),
            WireEntity(BlockPos(2, 0, 1), null, 0.0),
            WireEntity(BlockPos(2, 0, 2), null, 0.0),
        )
    )

    @Test
    fun findCurrentsStartFrom() {
        val pendingVisitDirections = mutableMapOf<BlockPos, MutableList<Direction>>()

        val result = findCurrentsStartFrom(stubBlockViewCycle, BlockPos(0, 0, 1), pendingVisitDirections)
        assert(result.size == 3)
        assert(result.contains(listOf(BlockPos(0, 0, 1), BlockPos(1, 0, 1))))
        assert(result.contains(listOf(BlockPos(0, 0, 1), BlockPos(0, 0, 2), BlockPos(1, 0, 2))))
        assert(result.contains(listOf(BlockPos(0, 0, 1), BlockPos(0, 0, 0), BlockPos(1, 0, 0))))

        val result2 = findCurrentsStartFrom(stubBlockViewCycle, BlockPos(1, 0, 1), pendingVisitDirections)
        assert(result2.size == 3)
        assert(result2.contains(listOf(BlockPos(1, 0, 1), BlockPos(2, 0, 1))))
        assert(result2.contains(listOf(BlockPos(1, 0, 1), BlockPos(1, 0, 2))))
        assert(result2.contains(listOf(BlockPos(1, 0, 1), BlockPos(1, 0, 0))))

        assert(result2.map { result.contains(it) }.all { !it })
    }
}