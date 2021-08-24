package com.fairchild_superconductor.magic_crystal.electric

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ElectricEntity(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?) :
    BlockEntity(type, pos, state), BlockEntityTicker<ElectricEntity> {
    override fun tick(world: World?, pos: BlockPos?, state: BlockState?, blockEntity: ElectricEntity?) {
        if (world != null && pos != null) {
            ElectricNetManager.onElectricBlockTicked(world, pos)
        }
    }
}

