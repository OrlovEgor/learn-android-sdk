package ru.orlovegor.clientprovider

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CheckEmptyOrNumberTest(
    private val text: String,
    private val expectedParams: Boolean
) {

    @Test
    fun parametrizedTestCheckString() {
        Assert.assertEquals(expectedParams, checkEmptyOrNumber(text))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun stringArguments() = listOf(
            arrayOf("Normal String", false),
            arrayOf("", false),
            arrayOf("12hgfd35", false),
            arrayOf("8847", true),
            arrayOf("443   534", false)
        )
    }
}