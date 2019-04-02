package mraqs.notification.data.model

import mraqs.notification.data.model.Gender.MALE
import mraqs.notification.data.model.WeightUnit.KG
import mraqs.notification.data.model.WeightUnit.LBS
import kotlin.math.roundToInt

data class User(
    var gender: Gender = MALE,
    var weight: Int = 20,
    var activityTime: Int = 0
)

enum class Gender {
    MALE,
    FEMALE
}

enum class WeightUnit {
    KG,
    LBS
}

fun String.toGender(): Gender {
    return when (this.toUpperCase()) {
        "MALE" -> Gender.MALE
        "FEMALE" -> Gender.FEMALE
        else -> MALE
    }
}

fun Int.toWeightUnit(): WeightUnit {
    return when (this) {
        0 -> KG
        1 -> LBS
        else -> KG
    }
}

fun Int.calculateWeight(unit: WeightUnit): Int {
    return if (unit == LBS)
        this.div(2.205).roundToInt()
    else this
}