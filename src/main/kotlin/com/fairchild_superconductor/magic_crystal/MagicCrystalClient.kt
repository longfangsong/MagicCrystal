package com.fairchild_superconductor.magic_crystal

import com.fairchild_superconductor.magic_crystal.computer.ComputerRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry

@Suppress("UNUSED")
object MagicCrystalClient : ClientModInitializer {
    override fun onInitializeClient() {
        println("Magic Crystal mod has been initialized on client side.")
        BlockEntityRendererRegistry.INSTANCE.register(COMPUTER_ENTITY) { _ -> ComputerRenderer() }
    }
}