package com.fairchild_superconductor.magic_crystal.electric.machine.debug

import com.fairchild_superconductor.magic_crystal.electric.machine.MachineEntity
import com.fairchild_superconductor.magic_crystal.mod_registry.Entity
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos

class DebugMachineEntity(pos: BlockPos?, state: BlockState?, override val resistance: Double) :
    MachineEntity(Entity.DEBUG_MACHINE_ENTITY, pos, state) {
}