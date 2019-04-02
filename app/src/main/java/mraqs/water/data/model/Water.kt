package mraqs.notification.data.model

import mraqs.notification.data.model.Gender.FEMALE
import mraqs.notification.data.model.Gender.MALE

data class Water(
    val progress: Int,
    val goal: Int,
    val volume: Int
) {

    companion object {

        fun calculateWaterAmount(user: User): Int {

            val amount: Int = (user.weight * when (user.gender) {
                MALE -> 35
                FEMALE -> 31
            }) + (user.activityTime * 300)

            return if (amount.rem(100) != 0) {
                amount + (100 - amount.rem(100))
            } else amount
        }
    }
}