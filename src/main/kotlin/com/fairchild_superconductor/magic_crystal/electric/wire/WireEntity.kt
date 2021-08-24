package com.fairchild_superconductor.magic_crystal.electric.wire

import com.fairchild_superconductor.magic_crystal.electric.ElectricEntity
import com.fairchild_superconductor.magic_crystal.electric.HasResistance
import com.fairchild_superconductor.magic_crystal.mod_registry.Entity
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

class WireEntity(
    pos: BlockPos?,
    state: BlockState?,
    override val resistance: Double
) : ElectricEntity(Entity.WIRE, pos, state), HasResistance {
}