package com.fairchild_superconductor.magic_crystal.electric

import net.minecraft.util.math.BlockPos

enum class Direction {
    EAST,  // X+
    UP,    // Y+
    SOUTH, // Z+
    WEST,  // X-
    DOWN,  // Y-
    NORTH; // Z-

    val reverse: Direction
        get() = when (this) {
            EAST -> WEST
            UP -> DOWN
            SOUTH -> NORTH
            WEST -> EAST
            DOWN -> UP
            NORTH -> SOUTH
        }
}

operator fun BlockPos.plus(direction: Direction): BlockPos {
    return when (direction) {
        Direction.DOWN -> BlockPos(this.x, this.y - 1, this.z)
        Direction.UP -> BlockPos(this.x, this.y + 1, this.z)
        Direction.NORTH -> BlockPos(this.x, this.y, this.z - 1)
        Direction.SOUTH -> BlockPos(this.x, this.y, this.z + 1)
        Direction.WEST -> BlockPos(this.x - 1, this.y, this.z)
        Direction.EAST -> BlockPos(this.x + 1, this.y, this.z)
    }
}