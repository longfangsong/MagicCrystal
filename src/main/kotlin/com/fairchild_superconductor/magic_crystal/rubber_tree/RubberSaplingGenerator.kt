package com.fairchild_superconductor.magic_crystal.rubber_tree

import com.fairchild_superconductor.magic_crystal.RUBBER_TREE_CONFIGURE
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import java.util.*

class RubberSaplingGenerator: SaplingGenerator() {
    override fun getTreeFeature(random: Random?, bees: Boolean): ConfiguredFeature<TreeFeatureConfig, *>? {
        println("getTreeFeature")
        println(RUBBER_TREE_CONFIGURE)
        return RUBBER_TREE_CONFIGURE
    }
}