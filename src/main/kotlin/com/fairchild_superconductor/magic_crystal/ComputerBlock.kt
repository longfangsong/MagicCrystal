package com.fairchild_superconductor.magic_crystal

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ComputerBlock(settings: FabricBlockSettings) : Block(settings) {
    override fun onUse(state: BlockState?, world: World?, pos: BlockPos?, player: PlayerEntity?, hand: Hand?, hit: BlockHitResult?): ActionResult {
        if (world?.isClient == true) {
            player?.sendMessage(LiteralText("Hello, world!"), false)
        }
        return ActionResult.SUCCESS
    }
}