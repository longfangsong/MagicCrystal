package com.fairchild_superconductor.magic_crystal.computer

import com.fairchild_superconductor.magic_crystal.COMPUTER_ENTITY
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class ComputerEntity(pos: BlockPos, state: BlockState) : BlockEntity(COMPUTER_ENTITY, pos, state) {
}