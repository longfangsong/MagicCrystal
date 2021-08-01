package com.fairchild_superconductor.magic_crystal

import com.fairchild_superconductor.magic_crystal.computer.ComputerBlock
import com.fairchild_superconductor.magic_crystal.computer.ComputerEntity
import com.fairchild_superconductor.magic_crystal.rubber_tree.RubberLogBlock
import com.fairchild_superconductor.magic_crystal.rubber_tree.RubberSaplingBlock
import com.fairchild_superconductor.magic_crystal.rubber_tree.RubberSaplingGenerator
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DataPool
import net.minecraft.util.math.intprovider.ConstantIntProvider
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeKeys
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.decorator.RangeDecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.heightprovider.UniformHeightProvider
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider
import net.minecraft.world.gen.trunk.StraightTrunkPlacer
import java.util.*


var COMPUTER_ENTITY: BlockEntityType<ComputerEntity>? = null

var RUBBER_TREE_CONFIGURE: ConfiguredFeature<TreeFeatureConfig, *>? = null

@Suppress("UNUSED")
object MagicCrystal : ModInitializer {
    const val MOD_ID = "magic_crystal"
    val COMPUTER_BLOCK: ComputerBlock = ComputerBlock(FabricBlockSettings.of(Material.STONE).hardness(4.0f))
    val TIN_ORE_BLOCK: Block = Block(FabricBlockSettings.of(Material.STONE).hardness(1.5f))
    val RUBBER_LEAVES_BLOCK: Block = Block(FabricBlockSettings.of(Material.LEAVES).hardness(1.0f))
    val RUBBER_LOG_BLOCK = RubberLogBlock()
    val RUBBER_SAPLING_BLOCK = RubberSaplingBlock(RubberSaplingGenerator(), FabricBlockSettings.of(Material.LEAVES))
    var COMPUTER_ITEM: BlockItem? = null
    override fun onInitialize() {
        val TIN_ORE_OVERWORLD: ConfiguredFeature<*, *> = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                    TIN_ORE_BLOCK.defaultState,
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

        Registry.register(
            Registry.BLOCK,
            Identifier(MOD_ID, "computer"),
            COMPUTER_BLOCK
        )
        Registry.register(
            Registry.BLOCK,
            Identifier(MOD_ID, "rubber_leaves"),
            RUBBER_LEAVES_BLOCK
        )
        Registry.register(
            Registry.BLOCK,
            Identifier(MOD_ID, "rubber_log"),
            RUBBER_LOG_BLOCK
        )
        Registry.register(
            Registry.BLOCK,
            Identifier(MOD_ID, "tin_ore"),
            TIN_ORE_BLOCK
        )
        COMPUTER_ITEM = Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "computer"),
            BlockItem(COMPUTER_BLOCK, Item.Settings().group(ItemGroup.MISC))
        )
        COMPUTER_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier(MOD_ID, "computer_entity"),
            FabricBlockEntityTypeBuilder.create({ pos, state -> ComputerEntity(pos, state) }, COMPUTER_BLOCK)
                .build(null)
        )
        Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "hammer"),
            Item(Item.Settings().group(ItemGroup.TOOLS).maxDamage(64))
        )
        Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "iron_plate"),
            Item(Item.Settings().group(ItemGroup.MISC))
        )
        Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "tin_ore"),
            BlockItem(TIN_ORE_BLOCK, Item.Settings().group(ItemGroup.MISC))
        )
        Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "raw_tin"),
            Item(Item.Settings().group(ItemGroup.MISC))
        )
        Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "rubber_leaves"),
            BlockItem(RUBBER_LEAVES_BLOCK, Item.Settings().group(ItemGroup.MISC))
        )
        Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "rubber_log"),
            BlockItem(RUBBER_LOG_BLOCK, Item.Settings().group(ItemGroup.MISC))
        )

        Registry.register(
            Registry.BLOCK,
            Identifier(MOD_ID, "rubber_sapling"),
            RUBBER_SAPLING_BLOCK
        )
        Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "rubber_sapling"),
            BlockItem(RUBBER_SAPLING_BLOCK, Item.Settings().group(ItemGroup.MISC))
        )

        val tinOreOverworld = RegistryKey.of(
            Registry.CONFIGURED_FEATURE_KEY,
            Identifier(MOD_ID, "tin_ore")
        )
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, tinOreOverworld.value, TIN_ORE_OVERWORLD)
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld(),
            GenerationStep.Feature.UNDERGROUND_ORES,
            tinOreOverworld
        )

        val rubberTreeKey = RegistryKey.of(
            Registry.CONFIGURED_FEATURE_KEY,
            Identifier(MOD_ID, "rubber_tree")
        )
        RUBBER_TREE_CONFIGURE = Feature.TREE.configure(
            TreeFeatureConfig.Builder(
                SimpleBlockStateProvider(RUBBER_LOG_BLOCK.defaultState),
                StraightTrunkPlacer(5, 3, 0),
                SimpleBlockStateProvider(RUBBER_LEAVES_BLOCK.defaultState),
                SimpleBlockStateProvider(RUBBER_SAPLING_BLOCK.defaultState),
                BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                TwoLayersFeatureSize(1, 0, 1)
            ).build()
        )

        Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            Identifier(MOD_ID, "rubber_tree"),
            RUBBER_TREE_CONFIGURE
        )
        val forestBiomes: Collection<RegistryKey<Biome>> =
            listOf(BiomeKeys.FOREST, BiomeKeys.WOODED_HILLS, BiomeKeys.FLOWER_FOREST)

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(forestBiomes),
            GenerationStep.Feature.VEGETAL_DECORATION, rubberTreeKey
        )

        println("Magic Crystal mod has been initialized.")
    }
}