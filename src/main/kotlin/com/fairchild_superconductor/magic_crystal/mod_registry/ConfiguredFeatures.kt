package com.fairchild_superconductor.magic_crystal.mod_registry

import com.fairchild_superconductor.magic_crystal.MagicCrystal
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.util.Identifier
import net.minecraft.util.math.intprovider.ConstantIntProvider
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.Heightmap
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.HeightmapDecoratorConfig
import net.minecraft.world.gen.decorator.RangeDecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.heightprovider.UniformHeightProvider
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

object ConfiguredFeatures {
    object Keys {
        var TIN_ORE_GENERATION: RegistryKey<ConfiguredFeature<*, *>> = RegistryKey.of(
            Registry.CONFIGURED_FEATURE_KEY,
            Identifier(MagicCrystal.MOD_ID, "tin_ore_generation")
        )
        var RUBBER_TREE: RegistryKey<ConfiguredFeature<*, *>> = RegistryKey.of(
            Registry.CONFIGURED_FEATURE_KEY,
            Identifier(MagicCrystal.MOD_ID, "rubber_tree")
        )
        var RUBBER_TREE_GENERATION: RegistryKey<ConfiguredFeature<*, *>> = RegistryKey.of(
            Registry.CONFIGURED_FEATURE_KEY,
            Identifier(MagicCrystal.MOD_ID, "rubber_tree_generation")
        )
    }

    var TIN_ORE_GENERATION: ConfiguredFeature<*, *> = Feature.ORE
        .configure(
            OreFeatureConfig(
                OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                Blocks.TIN_ORE.defaultState,
                9
            )
        )
        .range(
            RangeDecoratorConfig(
                UniformHeightProvider.create(YOffset.aboveBottom(0), YOffset.fixed(64))
            )
        )
        .spreadHorizontally()
        .repeat(20)
    var RUBBER_TREE: ConfiguredFeature<TreeFeatureConfig, *> = Feature.TREE.configure(
        TreeFeatureConfig.Builder(
            SimpleBlockStateProvider(Blocks.RUBBER_LOG.defaultState),
            StraightTrunkPlacer(5, 3, 0),
            SimpleBlockStateProvider(Blocks.RUBBER_LEAVES.defaultState),
            SimpleBlockStateProvider(Blocks.RUBBER_SAPLING.defaultState),
            BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().build()
    )
    var RUBBER_TREE_GENERATION: ConfiguredFeature<*, *> = RUBBER_TREE.decorate(
        Decorator.HEIGHTMAP.configure(
            HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)
        ).spreadHorizontally()
    )

    private fun registerConfiguredFeatures() {
        Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            Keys.TIN_ORE_GENERATION.value,
            TIN_ORE_GENERATION
        )
        Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            Keys.RUBBER_TREE.value,
            RUBBER_TREE
        )
        Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            Keys.RUBBER_TREE_GENERATION.value,
            RUBBER_TREE_GENERATION
        )
    }

    private fun registerBiomeModifications() {
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld(),
            GenerationStep.Feature.UNDERGROUND_ORES,
            Keys.TIN_ORE_GENERATION
        )
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld(),
            GenerationStep.Feature.VEGETAL_DECORATION,
            Keys.RUBBER_TREE_GENERATION
        )
    }

    fun registerAll() {
        registerConfiguredFeatures()
        registerBiomeModifications()
    }
}
