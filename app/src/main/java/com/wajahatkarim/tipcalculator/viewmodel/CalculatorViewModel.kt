package com.wajahatkarim.tipcalculator.viewmodel

import android.app.Application
import android.databinding.BaseObservable
import com.wajahatkarim.tipcalculator.R
import com.wajahatkarim.tipcalculator.model.RestaurantCalculator
import com.wajahatkarim.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(app: Application, val calculator: RestaurantCalculator = RestaurantCalculator()) : ObservableViewModel(app)
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
        txtBillPayment = getApplication<Application>().getString(R.string.dollar_amount, tc.checkAmount)
        txtTipAmount = getApplication<Application>().getString(R.string.dollar_amount, tc.tipAmount)
        txtGrandTotal = getApplication<Application>().getString(R.string.dollar_amount, tc.grandTotal)
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