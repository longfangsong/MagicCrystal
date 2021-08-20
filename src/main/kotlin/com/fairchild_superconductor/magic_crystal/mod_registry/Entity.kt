package com.fairchild_superconductor.magic_crystal.mod_registry

import com.fairchild_superconductor.magic_crystal.MagicCrystal
import com.fairchild_superconductor.magic_crystal.computer.ComputerEntity
import com.fairchild_superconductor.magic_crystal.electric.battery.debug.DebugBatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.machine.debug.DebugMachineEntity
import com.fairchild_superconductor.magic_crystal.electric.wire.WireEntity
import com.fairchild_superconductor.magic_crystal.rubber_tree.RubberLogEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Entity {
    var COMPUTER: BlockEntityType<ComputerEntity>? = null
    var RUBBER_LOG: BlockEntityType<RubberLogEntity>? = null
    var WIRE: BlockEntityType<WireEntity>? = null
    var DEBUG_MACHINE_ENTITY: BlockEntityType<DebugMachineEntity>? = null
    var DEBUG_BATTERY_ENTITY: BlockEntityType<DebugBatteryEntity>? = null
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
        WIRE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier(MagicCrystal.MOD_ID, "wire_entity"),
            FabricBlockEntityTypeBuilder.create(
                { pos, state -> WireEntity(pos, state, 0.0) },
                Blocks.WIRE
            ).build(null)
        )
        DEBUG_MACHINE_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier(MagicCrystal.MOD_ID, "debug_machine_entity"),
            FabricBlockEntityTypeBuilder.create(
                { pos, state -> DebugMachineEntity(pos, state, 0.0) },
                Blocks.DEBUG_MACHINE
            ).build(null)
        )
        DEBUG_BATTERY_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier(MagicCrystal.MOD_ID, "debug_battery_entity"),
            FabricBlockEntityTypeBuilder.create(
                { pos, state -> DebugBatteryEntity(pos, state, 0.0) },
                Blocks.DEBUG_BATTERY
            ).build(null)
        )
    }
}