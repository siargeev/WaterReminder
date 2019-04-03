package mraqs.water

import mraqs.water.util.WaterAmount
import mraqs.water.util.WaterAmount.Gender
import org.junit.*

class WaterDailyGoalTest {


    @Test
    fun amountIsCorrect() {
        val gender = Gender.MALE
        val weight = 80
        val time = 4
        val dailyGoal = WaterAmount.calculateWaterAmount(gender, weight, time)
        Assert.assertEquals(4000, dailyGoal)
    }
}