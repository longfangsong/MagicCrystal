package com.fairchild_superconductor.magic_crystal.computer

import com.fairchild_superconductor.magic_crystal.MagicCrystal
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Vec3f
import kotlin.math.sin


class ComputerRenderer : BlockEntityRenderer<ComputerEntity> {
    private val stack = ItemStack(MagicCrystal.COMPUTER_ITEM, 1)
    override fun render(entity: ComputerEntity?, tickDelta: Float, matrices: MatrixStack?, vertexConsumers: VertexConsumerProvider?, light: Int, overlay: Int) {
        matrices!!.push()
        val offset = sin((entity?.world?.time!! + tickDelta) / 8.0) / 4.0
        // Move the item
        matrices.translate(0.5, 1.25 + offset, 0.5)

        // Rotate the item

        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.world!!.time + tickDelta) * 4))
        val lightAbove = WorldRenderer.getLightmapCoordinates(entity.world, entity.pos.up())
        MinecraftClient.getInstance().itemRenderer
                .renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 1)
        // Mandatory call after GL calls
        matrices.pop()
    }
}