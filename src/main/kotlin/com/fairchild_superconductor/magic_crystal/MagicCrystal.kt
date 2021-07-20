package com.fairchild_superconductor.magic_crystal

import com.fairchild_superconductor.magic_crystal.computer.ComputerBlock
import com.fairchild_superconductor.magic_crystal.computer.ComputerEntity
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.decorator.RangeDecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.heightprovider.UniformHeightProvider


var COMPUTER_ENTITY: BlockEntityType<ComputerEntity>? = null

@Suppress("UNUSED")
object MagicCrystal : ModInitializer {
    const val MOD_ID = "magic_crystal"
    val COMPUTER_BLOCK: ComputerBlock = ComputerBlock(FabricBlockSettings.of(Material.STONE).hardness(4.0f))
    val TIN_ORE_BLOCK: Block = Block(FabricBlockSettings.of(Material.STONE).hardness(5.0f))
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
            Item(Item.Settings().group(ItemGroup.MISC))
        )
        Registry.register(
            Registry.BLOCK,
            Identifier(MOD_ID, "tin_ore"),
            TIN_ORE_BLOCK
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

        println("Magic Crystal mod has been initialized.")
    }
}