package com.fairchild_superconductor.magic_crystal.electric.battery.debug

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material

class DebugBatteryBlock : Block(FabricBlockSettings.of(Material.METAL).hardness(1.0f)) {
}