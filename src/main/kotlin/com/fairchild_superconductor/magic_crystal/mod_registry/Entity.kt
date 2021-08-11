package com.fairchild_superconductor.magic_crystal.mod_registry

import com.fairchild_superconductor.magic_crystal.MagicCrystal
import com.fairchild_superconductor.magic_crystal.computer.ComputerEntity
import com.fairchild_superconductor.magic_crystal.rubber_tree.RubberLogEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Entity {
    var COMPUTER: BlockEntityType<ComputerEntity>? = null
    var RUBBER_LOG: BlockEntityType<RubberLogEntity>? = null
    fun registryAll() {
        COMPUTER = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier(MagicCrystal.MOD_ID, "computer_entity"),
            FabricBlockEntityTypeBuilder.create(
                { pos, state -> ComputerEntity(pos, state) },
                Blocks.COMPUTER
            ).build(null)
        )
        RUBBER_LOG = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier(MagicCrystal.MOD_ID, "rubber_log"),
            FabricBlockEntityTypeBuilder.create(
                { pos, state -> RubberLogEntity(pos, state) },
                Blocks.RUBBER_LOG
            ).build(null)
        )
    }
}