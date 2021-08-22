package com.fairchild_superconductor.magic_crystal.electric

import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

object ElectricNetManager {
    private val electricNets: MutableList<ElectricNet> = mutableListOf()

    @Synchronized
    fun onElectricBlockTicked(blockView: BlockView, blockPos: BlockPos) {
        if (!electricNets.any { it.visited.contains(blockPos) }) {
            val entity = blockView.getBlockEntity(blockPos)
            assert(entity is ElectricBlockEntity)
            val newNet = ElectricNet.fromBFS(blockView, entity as ElectricBlockEntity)

            if (newNet != null) {
                electricNets += newNet
            }
        }
    }

    fun getDetail(blockView: BlockView, blockPos: BlockPos): String? {
        return electricNets.find { it.visited.contains(blockPos) }?.getDetail(blockView, blockPos)
    }
}