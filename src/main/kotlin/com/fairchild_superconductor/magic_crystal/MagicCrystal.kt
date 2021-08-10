package com.fairchild_superconductor.magic_crystal

import com.fairchild_superconductor.magic_crystal.mod_registry.registerAll
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.util.Identifier


@Suppress("UNUSED")
object MagicCrystal : ModInitializer {
    const val MOD_ID = "magic_crystal"

    override fun onInitialize() {
        registerAll()
        TagRegistry.block(Identifier(MOD_ID, "rubber_log"))
        println("Magic Crystal mod has been initialized.")
    }
}