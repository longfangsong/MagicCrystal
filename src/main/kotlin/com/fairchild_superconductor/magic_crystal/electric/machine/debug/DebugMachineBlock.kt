package com.fairchild_superconductor.magic_crystal.electric.machine.debug

import com.fairchild_superconductor.magic_crystal.electric.ElectricBlock
import com.fairchild_superconductor.magic_crystal.electric.battery.debug.DebugBatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.wire.WireEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class DebugMachineBlock : ElectricBlock(FabricBlockSettings.of(Material.METAL).hardness(1.0f)) {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): DebugMachineEntity {
        return DebugMachineEntity(pos, state, 10.0)
    }
}