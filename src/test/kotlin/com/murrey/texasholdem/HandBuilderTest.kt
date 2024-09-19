import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class StringHelperParameterTest(private val input: Int, private val expectedValue: Boolean) {

    @Test
    fun testParameterized_IsPositiveNumberOrNot() {
        val SH = StringHelper()
        val result = SH.isPositiveNumber(input)
        assertEquals(expectedValue, result)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf(-1, false),
                arrayOf(3, true)
            )
        }
    }
}

class StringHelper {
    fun isPositiveNumber(number: Int): Boolean {
        return number > 0
    }
}