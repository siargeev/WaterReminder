package mraqs.water.util

import mraqs.water.util.WaterAmount.Gender.FEMALE
import mraqs.water.util.WaterAmount.Gender.MALE

class WaterAmount {
    companion object {
        fun calculateWaterAmount(gender: Gender, weight: Int, activityTime: Int): Int {

            val amount: Int = (weight * when (gender) {
                MALE -> 35
                FEMALE -> 31
            }) + (activityTime * 300)

            return if (amount.rem(100) != 0) {
                amount + (100 - amount.rem(100))
            } else amount
        }
    }

    enum class Gender {
        MALE,
        FEMALE
    }
}