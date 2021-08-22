package com.fairchild_superconductor.magic_crystal.electric

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.random.Random

abstract class ElectricBlockEntity(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?) :
    BlockEntity(type, pos, state), BlockEntityTicker<ElectricBlockEntity> {
    override fun tick(world: World?, pos: BlockPos?, state: BlockState?, blockEntity: ElectricBlockEntity?) {
        if (world != null && pos != null) {
            ElectricNetManager.onElectricBlockTicked(world, pos)
        }
    }
}

interface HasResistance {
    val resistance: Double
}
