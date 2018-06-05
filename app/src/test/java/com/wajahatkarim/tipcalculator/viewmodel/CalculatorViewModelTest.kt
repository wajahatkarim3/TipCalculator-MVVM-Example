package com.wajahatkarim.tipcalculator.viewmodel

import android.app.Application
import com.wajahatkarim.tipcalculator.R
import com.wajahatkarim.tipcalculator.model.RestaurantCalculator
import com.wajahatkarim.tipcalculator.model.TipCalculation
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CalculatorViewModelTest
{
    lateinit var calculatorViewModel: CalculatorViewModel

    @Mock
    lateinit var mockCalculator: RestaurantCalculator

    @Mock
    lateinit var application: Application

    @Before
    fun setup()
    {
        MockitoAnnotations.initMocks(this)
        stubResource(0.0, "$0.00")
        calculatorViewModel = CalculatorViewModel(application, mockCalculator)
    }

    private fun stubResource(given: Double, returnStub: String)
    {
        `when`(application.getString(R.string.dollar_amount, given)).thenReturn(returnStub)
    }

    @Test
    fun testCalculateTip()
    {
        calculatorViewModel.txtCheckAmount = "10.00"
        calculatorViewModel.txtTipPercentage = "15"

        val stub = TipCalculation(checkAmount = 10.00, tipPct = 15, tipAmount = 1.5, grandTotal = 11.5)
        `when`(mockCalculator.calculateTip(10.00, 15)).thenReturn(stub)

        stubResource(10.0, "$10.00")
        stubResource(1.5, "$1.50")
        stubResource(11.5, "$11.50")

        calculatorViewModel.calculateTip()

        //assertEquals(stub, calculatorViewModel.tipCalculation)
        assertEquals("$10.00", calculatorViewModel.txtBillPayment)
        assertEquals("$1.50", calculatorViewModel.txtTipAmount)
        assertEquals("$11.50", calculatorViewModel.txtGrandTotal)
    }

    @Test
    fun testCalculateTipBadTipPercent()
    {
        calculatorViewModel.txtCheckAmount = "10.00"
        calculatorViewModel.txtTipPercentage = ""           // Bad or empty tip

        verify(mockCalculator, never()).calculateTip(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyInt())
    }

    @Test
    fun testCalculateTipBadCheckAmount()
    {
        calculatorViewModel.txtCheckAmount = ""             // Bad or check amount
        calculatorViewModel.txtTipPercentage = "1.5"

        verify(mockCalculator, never()).calculateTip(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyInt())
    }
}