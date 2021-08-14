package com.fairchild_superconductor.magic_crystal.rubber_tree

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.World


class RubberLogBlock : PillarBlock(
    Settings.of(
        Material.WOOD
    ) { MapColor.SPRUCE_BROWN }
        .strength(2.0f, 2f)
        .sounds(BlockSoundGroup.WOOD)
        .ticksRandomly().nonOpaque()
), BlockEntityProvider {
    companion object {
        val CAN_PRODUCING_RUBBER = BooleanProperty.of("can_producing_rubber")
        val START_PRODUCING_RUBBER = BooleanProperty.of("start_producing_rubber")
    }

    init {
        defaultState = getStateManager().defaultState
            .with(CAN_PRODUCING_RUBBER, true)
            .with(START_PRODUCING_RUBBER, false)
            .with(AXIS, Direction.Axis.Y)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        super.appendProperties(builder)
        builder?.add(CAN_PRODUCING_RUBBER)?.add(START_PRODUCING_RUBBER)
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        super.onUse(state, world, pos, player, hand, hit)
        val stack = player?.getStackInHand(Hand.MAIN_HAND)
        val isSharpTool = FabricToolTags.SWORDS.contains(stack?.item) ||
                FabricToolTags.AXES.contains(stack?.item)
        if (isSharpTool && state?.get(CAN_PRODUCING_RUBBER) == true && state.get(START_PRODUCING_RUBBER) == false) {
            world?.setBlockState(pos, state.with(START_PRODUCING_RUBBER, true))
            if (stack?.damage != null) {
                stack.damage += 1
            }
        } else if (state?.get(START_PRODUCING_RUBBER) == true) {
            val entity: RubberLogEntity = world?.getBlockEntity(pos) as RubberLogEntity
            if (Registry.ITEM.getId(stack?.item).toString() == "minecraft:bowl") {
                entity.setStack(0, stack)
            } else {
                player?.inventory?.offerOrDrop(entity.getStack(0))
                entity.removeStack(0)
            }
        }
        return ActionResult.SUCCESS
    }

    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity {
        return RubberLogEntity(pos, state)
    }

    override fun getRenderType(state: BlockState?): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { world1: World?, pos: BlockPos?, state1: BlockState?, blockEntity: T ->
            (blockEntity as RubberLogEntity).tick(
                world1,
                pos,
                state1,
                blockEntity as RubberLogEntity
            )
        }
    }

    override fun onPlaced(
        world: World?,
        pos: BlockPos?,
        state: BlockState?,
        placer: LivingEntity?,
        itemStack: ItemStack?
    ) {
        world?.setBlockState(pos, state?.with(CAN_PRODUCING_RUBBER, false)?.with(START_PRODUCING_RUBBER, false))
    }
}