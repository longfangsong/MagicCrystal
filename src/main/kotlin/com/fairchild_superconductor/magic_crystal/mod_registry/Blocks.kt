package com.fairchild_superconductor.magic_crystal.mod_registry

import com.fairchild_superconductor.magic_crystal.MagicCrystal
import com.fairchild_superconductor.magic_crystal.computer.ComputerBlock
import com.fairchild_superconductor.magic_crystal.rubber_tree.RubberLogBlock
import com.fairchild_superconductor.magic_crystal.rubber_tree.RubberSaplingBlock
import com.fairchild_superconductor.magic_crystal.rubber_tree.RubberSaplingGenerator
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Blocks {
    val COMPUTER: ComputerBlock = ComputerBlock(FabricBlockSettings.of(Material.STONE).hardness(4.0f))
    val TIN_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).hardness(1.5f))
    val RUBBER_LOG = RubberLogBlock()
    val RUBBER_LEAVES = LeavesBlock(
        FabricBlockSettings.of(Material.LEAVES).hardness(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS)
            .nonOpaque()
    )
    val RUBBER_SAPLING = RubberSaplingBlock(
        RubberSaplingGenerator(),
        FabricBlockSettings.of(Material.LEAVES).ticksRandomly().breakInstantly()
    )

    private fun registerBlock(block: Block, path: String): Block {
        return Registry.register(Registry.BLOCK, Identifier(MagicCrystal.MOD_ID, path), block)
    }

    fun registerAll() {
        registerBlock(COMPUTER, "computer")
        registerBlock(TIN_ORE, "tin_ore")
        registerBlock(RUBBER_LOG, "rubber_log")
        registerBlock(RUBBER_LEAVES, "rubber_leaves")
        registerBlock(RUBBER_SAPLING, "rubber_sapling")
    }
}