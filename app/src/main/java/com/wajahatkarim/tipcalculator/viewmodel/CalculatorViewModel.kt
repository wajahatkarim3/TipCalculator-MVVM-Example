package com.wajahatkarim.tipcalculator.viewmodel

import com.wajahatkarim.tipcalculator.model.RestaurantCalculator
import com.wajahatkarim.tipcalculator.model.TipCalculation

class CalculatorViewModel(val calculator: RestaurantCalculator = RestaurantCalculator()) {

    var txtCheckAmount = ""
    var txtTipPercentage = ""

    var tipCalculation = TipCalculation()

    fun calculateTip()
    {
        val checkAmount = txtCheckAmount.toDoubleOrNull()
        val tipPct = txtTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPct != null)
        {
            tipCalculation = calculator.calculateTip(checkAmount, tipPct)
        }
    }
}