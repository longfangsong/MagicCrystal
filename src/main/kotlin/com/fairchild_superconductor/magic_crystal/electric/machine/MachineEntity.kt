package com.fairchild_superconductor.magic_crystal.electric.machine

import com.fairchild_superconductor.magic_crystal.electric.ElectricEntity
import com.fairchild_superconductor.magic_crystal.electric.HasResistance
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos

abstract class MachineEntity(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?) :
    ElectricEntity(type, pos, state), HasResistance