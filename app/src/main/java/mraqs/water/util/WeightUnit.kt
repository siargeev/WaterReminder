package mraqs.water.util

import mraqs.water.util.WeightUnit.KG
import mraqs.water.util.WeightUnit.LBS
import kotlin.math.roundToInt

enum class WeightUnit {
    KG,
    LBS
}

fun Int.toWeight(unit: WeightUnit): Int {
    return if (unit == LBS)
        this.plus(44).div(2.205).roundToInt()
    else this.plus(20)
}

fun Int.toWeightUnit(): WeightUnit {
    return when (this) {
        0 -> KG
        1 -> LBS
        else -> KG
    }
}