package com.wajahatkarim.tipcalculator.viewmodel

import android.app.Application
import android.databinding.BaseObservable
import com.wajahatkarim.tipcalculator.R
import com.wajahatkarim.tipcalculator.model.RestaurantCalculator
import com.wajahatkarim.tipcalculator.model.TipCalculation

class CalculatorViewModel(val app: Application, val calculator: RestaurantCalculator = RestaurantCalculator()) : BaseObservable()
{

    var txtCheckAmount = ""
    var txtTipPercentage = ""

    var txtBillPayment = ""
    var txtTipAmount = ""
    var txtGrandTotal = ""

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation)
    {
        txtBillPayment = app.getString(R.string.dollar_amount, tc.checkAmount)
        txtTipAmount = app.getString(R.string.dollar_amount, tc.tipAmount)
        txtGrandTotal = app.getString(R.string.dollar_amount, tc.grandTotal)
    }

    fun calculateTip()
    {
        val checkAmount = txtCheckAmount.toDoubleOrNull()
        val tipPct = txtTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPct != null)
        {
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
            clearInputs()
        }
    }

    fun clearInputs()
    {
        txtCheckAmount = "0.00"
        txtTipPercentage = "0"
        notifyChange()
    }
}