package com.fairchild_superconductor.magic_crystal.electric.wire

import com.fairchild_superconductor.magic_crystal.electric.ElectricBlock
import com.fairchild_superconductor.magic_crystal.electric.ElectricNetManager
import com.fairchild_superconductor.magic_crystal.mod_registry.Items
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class WireBlock : ElectricBlock(FabricBlockSettings.of(Material.METAL).hardness(1.0f).nonOpaque()) {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): WireEntity {
        return WireEntity(pos, state, 0.02)
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        if (player?.isHolding(Items.MULTIMETER) == true && world != null && pos != null) {
            val detail = ElectricNetManager.getDetail(world, pos)
            if (detail != null) {
                player.sendMessage(LiteralText(detail), false)
                return ActionResult.SUCCESS
            }
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }
}