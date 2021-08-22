package com.fairchild_superconductor.magic_crystal.electric.battery.debug

import com.fairchild_superconductor.magic_crystal.electric.machine.debug.DebugMachineEntity
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

class DebugBatteryBlock : Block(FabricBlockSettings.of(Material.METAL).hardness(1.0f)), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity {
        return DebugBatteryEntity(pos, state, 10.0)
    }

    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { world1: World?, pos: BlockPos?, state1: BlockState?, blockEntity: T ->
            (blockEntity as DebugBatteryEntity).tick(
                world1,
                pos,
                state1,
                blockEntity
            )
        }
    }
}