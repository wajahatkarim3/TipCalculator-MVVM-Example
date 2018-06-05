package com.wajahatkarim.tipcalculator.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RestaurantCalculatorTest {

    lateinit var calculator: RestaurantCalculator

    @Before
    fun setup()
    {
        calculator = RestaurantCalculator()
    }

    @Test
    fun testCalculatorTip()
    {

        val baseTc = TipCalculation(checkAmount = 10.00)

        val testVals = listOf(baseTc.copy(tipPct = 25, tipAmount = 2.5, grandTotal = 12.50),
                baseTc.copy(tipPct = 15, tipAmount = 1.5, grandTotal = 11.50),
                baseTc.copy(tipPct = 18, tipAmount = 1.8, grandTotal = 11.80))


        testVals.forEach {
            assertEquals(it, calculator.calculateTip(it.checkAmount, it.tipPct))
        }


    }
}