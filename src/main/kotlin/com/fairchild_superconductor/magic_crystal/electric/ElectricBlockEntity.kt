package com.fairchild_superconductor.magic_crystal.electric

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos

abstract class ElectricBlockEntity(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?) :
    BlockEntity(type, pos, state)

interface HasResistance {
    val resistance: Double
}
