package com.fairchild_superconductor.magic_crystal.electric

import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ElectricBlock(settings: Settings?) : Block(settings), BlockEntityProvider {
    abstract override fun createBlockEntity(pos: BlockPos?, state: BlockState?): ElectricEntity
    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { world1: World?, pos: BlockPos?, state1: BlockState?, blockEntity: T ->
            (blockEntity as ElectricEntity).tick(
                world1,
                pos,
                state1,
                blockEntity
            )
        }
    }
}