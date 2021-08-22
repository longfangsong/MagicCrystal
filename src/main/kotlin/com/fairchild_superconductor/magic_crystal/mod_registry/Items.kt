package com.fairchild_superconductor.magic_crystal.mod_registry

import com.fairchild_superconductor.magic_crystal.MagicCrystal
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Items {
    var HAMMER: Item = registerItem("hammer")
    var RAW_TIN: Item = registerItem("raw_tin")
    var IRON_PLATE: Item = registerItem("iron_plate")
    var RUBBER_BOWL: Item = registerItem("rubber_bowl")
    var MULTIMETER: Item = registerItem("multimeter")
    var TIN_ORE: BlockItem = registerBlockItem("tin_ore", Blocks.TIN_ORE)
    var RUBBER_LOG: BlockItem = registerBlockItem("rubber_log", Blocks.RUBBER_LOG)
    var RUBBER_LEAVES: BlockItem = registerBlockItem("rubber_leaves", Blocks.RUBBER_LEAVES)
    var RUBBER_SAPLING: BlockItem = registerBlockItem("rubber_sapling", Blocks.RUBBER_SAPLING)

    // items with entity needs to be inited later
    var COMPUTER: BlockItem? = null
    var DEBUG_BATTERY: BlockItem? = null
    var DEBUG_MACHINE: BlockItem? = null
    var WIRE: BlockItem? = null
    private fun registerItem(path: String): Item {
        return Registry.register(
            Registry.ITEM,
            Identifier(MagicCrystal.MOD_ID, path),
            Item(Item.Settings().group(ItemGroup.MISC))
        )
    }

    private fun registerBlockItem(path: String, block: Block): BlockItem {
        return Registry.register(
            Registry.ITEM,
            Identifier(MagicCrystal.MOD_ID, path),
            // todo: Add our own Item Group
            BlockItem(block, Item.Settings().group(ItemGroup.MISC))
        )
    }

    fun registerAll() {
        COMPUTER = registerBlockItem("computer", Blocks.COMPUTER)
        DEBUG_BATTERY = registerBlockItem("debug_battery", Blocks.DEBUG_BATTERY)
        DEBUG_MACHINE = registerBlockItem("debug_machine", Blocks.DEBUG_MACHINE)
        WIRE = registerBlockItem("wire", Blocks.WIRE)
    }
}
