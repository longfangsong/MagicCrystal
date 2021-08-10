package com.fairchild_superconductor.magic_crystal

import com.fairchild_superconductor.magic_crystal.computer.ComputerRenderer
import com.fairchild_superconductor.magic_crystal.mod_registry.Blocks
import com.fairchild_superconductor.magic_crystal.mod_registry.Entity
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry
import net.minecraft.client.render.RenderLayer

@Suppress("UNUSED")
object MagicCrystalClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(Entity.COMPUTER) { ComputerRenderer() }
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.RUBBER_SAPLING, RenderLayer.getTranslucent())
        println("Magic Crystal mod has been initialized on client side.")
    }
}