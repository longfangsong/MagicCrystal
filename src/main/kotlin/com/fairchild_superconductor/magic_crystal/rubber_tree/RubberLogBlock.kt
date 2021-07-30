package com.fairchild_superconductor.magic_crystal.rubber_tree

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import net.minecraft.block.PillarBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class RubberLogBlock : PillarBlock(
    Settings.of(
        Material.WOOD
    ) { MapColor.SPRUCE_BROWN }
        .strength(2.0f, 2f)
        .sounds(BlockSoundGroup.WOOD)
        .ticksRandomly()
) {
    init {
        defaultState = defaultState.with(AXIS, Direction.Axis.Y)
        FlammableBlockRegistry.getDefaultInstance().add(this, 5, 5)
    }

    override fun onBreak(worldIn: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        val i = 4
        val j = i + 1
        val chunk = ChunkPos(pos)
        if (worldIn is ServerWorld && worldIn.isRegionLoaded(pos.add(-j, -j, -j), pos.add(j, j, j))) {
            for (blockpos in BlockPos.iterate(pos.add(-i, -i, -i), pos.add(i, i, i))) {
                val maybeLeafBlockState = worldIn.getBlockState(blockpos)
                if (maybeLeafBlockState.isIn(BlockTags.LEAVES)) {
                    maybeLeafBlockState.scheduledTick(worldIn, blockpos, worldIn.getRandom())
                    maybeLeafBlockState.randomTick(worldIn, blockpos, worldIn.getRandom())
                }
            }
        }
        super.onBreak(worldIn, pos, state, player)
    }
}
