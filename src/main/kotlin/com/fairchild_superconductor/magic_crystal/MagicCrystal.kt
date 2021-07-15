package com.fairchild_superconductor.magic_crystal

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry


var COMPUTER_ENTITY: BlockEntityType<ComputerEntity>? = null

@Suppress("UNUSED")
object MagicCrystal : ModInitializer {
    const val MOD_ID = "magic_crystal"
    val COMPUTER_BLOCK: ComputerBlock = ComputerBlock(FabricBlockSettings.of(Material.STONE).hardness(4.0f))
    var COMPUTER_ITEM: BlockItem? = null
    override fun onInitialize() {
        println("Magic Crystal mod has been initialized.")

        Registry.register(
                Registry.BLOCK,
                Identifier(MOD_ID, "computer"),
                COMPUTER_BLOCK
        )
        COMPUTER_ITEM = Registry.register(
                Registry.ITEM,
                Identifier(MOD_ID, "computer"),
                BlockItem(COMPUTER_BLOCK, Item.Settings().group(ItemGroup.MISC))
        )
        COMPUTER_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                Identifier(MOD_ID, "computer_entity"),
                FabricBlockEntityTypeBuilder.create({ pos, state -> ComputerEntity(pos, state) }, COMPUTER_BLOCK).build(null)
        )
    }
}