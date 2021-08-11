package com.fairchild_superconductor.magic_crystal.rubber_tree

import com.fairchild_superconductor.magic_crystal.mod_registry.Entity
import com.fairchild_superconductor.magic_crystal.mod_registry.Items as ModItems
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


class RubberLogEntity(pos: BlockPos?, state: BlockState?) :
    BlockEntity(Entity.RUBBER_LOG, pos, state),
    Inventory,
    BlockEntityTicker<RubberLogEntity> {
    private var containedItem = ItemStack(Items.BOWL, 0)
    private var ticksPassed = 0
    override fun clear() {
        ticksPassed = 0
        containedItem.count = 0
    }

    override fun size(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return containedItem.isEmpty
    }

    override fun getStack(slot: Int): ItemStack {
        assert(slot == 0)
        return containedItem
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        assert(slot == 0)
        val removed = containedItem
        containedItem = ItemStack(Items.BOWL, 0)
        markDirty()
        return removed
    }

    override fun removeStack(slot: Int): ItemStack {
        ticksPassed = 0
        return removeStack(slot, 1)
    }

    override fun setStack(slot: Int, stack: ItemStack?) {
        assert(slot == 0)
        if (containedItem.isEmpty) {
            containedItem.count += 1
            stack?.count = (stack?.count ?: 1) - 1
        }
    }

    override fun canPlayerUse(player: PlayerEntity?): Boolean {
        return true
    }

    override fun readNbt(nbt: NbtCompound?) {
        super.readNbt(nbt)
        if (nbt?.getBoolean("HasBowl") == true) {
            containedItem = ItemStack(Items.BOWL, 1)
            ticksPassed = nbt.getInt("TicksPassed")
        }
    }

    override fun writeNbt(nbt: NbtCompound?): NbtCompound {
        nbt?.putBoolean("HasBowl", !isEmpty)
        nbt?.putInt("TicksPassed", ticksPassed)
        return super.writeNbt(nbt)
    }

    override fun tick(world: World?, pos: BlockPos?, state: BlockState?, blockEntity: RubberLogEntity?) {
        if (!containedItem.isEmpty && ticksPassed < 100) {
            ticksPassed += 1
        } else if (ticksPassed >= 100) {
            containedItem = ItemStack(ModItems.RUBBER_BOWL, 1)
        }
    }
}