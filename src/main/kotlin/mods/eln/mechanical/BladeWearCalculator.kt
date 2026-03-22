package mods.eln.mechanical

import mods.eln.item.TurbineBladeDescriptor

// Tune BASE_WEAR_RATE to adjust overall blade lifetime. Adjust tier resistances to widen the gap between tiers.
object BladeWearCalculator {

    // Base durability consumed per second.
    const val BASE_WEAR_RATE = 0.02

    fun wearPerSecond(
        temperatureFactor: Double,
        cleanlinessFactor: Double,
        blade: TurbineBladeDescriptor
    ): Double {
        val tempComponent = 1.0 + temperatureFactor * (2.0 / blade.temperatureResistance)
        val corrComponent = 1.0 + cleanlinessFactor * (2.0 / blade.corrosionResistance)
        return BASE_WEAR_RATE * tempComponent * corrComponent
    }
}
