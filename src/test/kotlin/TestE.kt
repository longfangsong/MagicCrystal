import com.fairchild_superconductor.magic_crystal.electric.ElectricNet
import com.fairchild_superconductor.magic_crystal.electric.battery.debug.DebugBatteryEntity
import com.fairchild_superconductor.magic_crystal.electric.machine.debug.DebugMachineEntity
import com.fairchild_superconductor.magic_crystal.electric.wire.WireEntity
import net.minecraft.util.math.BlockPos
import kotlin.test.Test

class TestE {
    @Test
    fun net() {
        val stubBlockView = ElectricTest.StubBlockView(
            listOf(
                DebugBatteryEntity(BlockPos(0, 0, 0), null, 10.0),
                WireEntity(BlockPos(0, 0, 1), null, 0.02),
                WireEntity(BlockPos(0, 0, 2), null, 0.02),
                WireEntity(BlockPos(0, 0, 3), null, 0.02),
                DebugMachineEntity(BlockPos(0, 0, 4), null, 10.0),
                WireEntity(BlockPos(1, 0, 1), null, 0.02),
                WireEntity(BlockPos(1, 0, 2), null, 0.02),
                WireEntity(BlockPos(1, 0, 3), null, 0.02),
                DebugMachineEntity(BlockPos(2, 0, 3), null, 10.0),
            )
        )
        val result = ElectricNet.fromBFS(stubBlockView, stubBlockView.getBlockEntity(BlockPos(0, 0, 0))!!)
        println(result!!.solution)
    }
}