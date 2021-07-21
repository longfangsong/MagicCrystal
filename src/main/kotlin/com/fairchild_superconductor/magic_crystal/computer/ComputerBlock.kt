package com.fairchild_superconductor.magic_crystal.computer

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class ComputerBlock(settings: FabricBlockSettings) : Block(settings), BlockEntityProvider {
    override fun onUse(state: BlockState?, world: World?, pos: BlockPos?, player: PlayerEntity?, hand: Hand?, hit: BlockHitResult?): ActionResult {
        if (world?.isClient == true) {
            player?.sendMessage(LiteralText("Hello, world!"), false)
        }
        val newPos = BlockPos(Vec3d(pos!!.x + 1.0, pos.y.toDouble(), pos.z.toDouble()))
        world?.removeBlock(pos, true)
        world?.setBlockState(newPos, state)
        player?.addExperience(100)
        return ActionResult.SUCCESS
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return ComputerEntity(pos, state)
    }
}