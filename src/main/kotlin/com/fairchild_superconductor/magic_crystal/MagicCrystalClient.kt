package com.fairchild_superconductor.magic_crystal

import com.fairchild_superconductor.magic_crystal.computer.ComputerRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry
import net.minecraft.client.render.RenderLayer

@Suppress("UNUSED")
object MagicCrystalClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(COMPUTER_ENTITY) { _ -> ComputerRenderer() }
        BlockRenderLayerMap.INSTANCE.putBlock(MagicCrystal.RUBBER_SAPLING_BLOCK, RenderLayer.getTranslucent());
        println("Magic Crystal mod has been initialized on client side.")
    }
}