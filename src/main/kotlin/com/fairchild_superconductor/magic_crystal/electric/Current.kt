package com.fairchild_superconductor.magic_crystal.electric

import com.fairchild_superconductor.magic_crystal.electric.battery.BatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.machine.MachineEntity
import com.fairchild_superconductor.magic_crystal.electric.wire.WireEntity

class Current(
    inner: List<ElectricBlockEntity>
) : HasResistance {
    private val content: List<ElectricBlockEntity>

    init {
        content = if (inner.first() is MachineEntity || inner.last() is BatteryEntity)
            inner.reversed()
        else inner
    }

    override val resistance: Double
        get() =
            content.sumOf {
                (it as? HasResistance)?.resistance ?: 0.0
            } - ((content.first() as? WireEntity)?.resistance
                ?: 0.0) / 2.0 - ((content.last() as? WireEntity)?.resistance ?: 0.0) / 2.0

    val start: ElectricBlockEntity
        get() = content.first()

    val end: ElectricBlockEntity
        get() = content.last()

    override fun toString(): String {
        return content.map { it.pos }
            .joinToString("->")
    }


}