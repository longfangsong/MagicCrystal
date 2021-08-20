package com.fairchild_superconductor.magic_crystal.electric.battery.debug

import com.fairchild_superconductor.magic_crystal.electric.battery.BatteryEntity
import com.fairchild_superconductor.magic_crystal.mod_registry.Entity
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

class DebugBatteryEntity(pos: BlockPos?, state: BlockState?, potential: Double) :
    BatteryEntity(Entity.DEBUG_BATTERY_ENTITY, pos, state, potential) {
}