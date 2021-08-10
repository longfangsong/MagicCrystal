package com.fairchild_superconductor.magic_crystal.computer

import com.fairchild_superconductor.magic_crystal.mod_registry.Entity
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class ComputerEntity(pos: BlockPos, state: BlockState) : BlockEntity(Entity.COMPUTER, pos, state) {
}