package com.fairchild_superconductor.magic_crystal.electric.battery

import com.fairchild_superconductor.magic_crystal.electric.ElectricBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos

abstract class BatteryEntity(
    type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?,
    val potential: Double
) :
    ElectricBlockEntity(type, pos, state) {
}