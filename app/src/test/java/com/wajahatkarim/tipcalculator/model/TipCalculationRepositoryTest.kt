package com.wajahatkarim.tipcalculator.model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TipCalculationRepositoryTest {

    lateinit var repository: TipCalculationRepository

    @get:Rule
    var testRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = TipCalculationRepository()
    }

    @Test
    fun testSaveTip() {
        val tip = TipCalculation(locationName = "Pancake Paradise",
                checkAmount = 100.0, tipPct = 25,
                tipAmount = 25.0, grandTotal = 125.0)

        repository.saveTipCalculation(tip)

        assertEquals(tip, repository.getTipCalculationByName(tip.locationName))
    }

    @Test
    fun testLoadSavedTipCalculations() {
        val tip1 = TipCalculation(
                locationName = "Pancake Paradise",
                checkAmount = 100.0, tipPct = 25,
                tipAmount = 25.0, grandTotal = 125.0
        )

        val tip2 = TipCalculation(
                locationName = "Veggie Sensation",
                checkAmount = 100.0, tipPct = 25,
                tipAmount = 25.0, grandTotal = 125.0
        )

        repository.saveTipCalculation(tip1)
        repository.saveTipCalculation(tip2)

        repository.loadSavedTipCalculations().observeForever { tipCalculations ->
            assertEquals(2, tipCalculations?.size)
        }
    }
}