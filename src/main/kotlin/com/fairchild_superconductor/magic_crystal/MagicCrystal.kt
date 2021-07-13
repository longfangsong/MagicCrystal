package com.fairchild_superconductor.magic_crystal

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object MagicCrystal : ModInitializer {
    private const val MOD_ID = "magic_crystal"
    private val ComputerBlock = ComputerBlock(FabricBlockSettings.of(Material.STONE).hardness(4.0f))

    override fun onInitialize() {
        println("Magic Crystal mod has been initialized.")

        Registry.register(
                Registry.BLOCK,
                Identifier(MOD_ID, "computer"),
                ComputerBlock
        )
        Registry.register(
                Registry.ITEM,
                Identifier(MOD_ID, "computer"),
                BlockItem(ComputerBlock, Item.Settings().group(ItemGroup.MISC))
        )
    }
}