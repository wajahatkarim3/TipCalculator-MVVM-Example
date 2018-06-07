package com.wajahatkarim.tipcalculator.model

import android.arch.lifecycle.LiveData
import java.math.RoundingMode

class RestaurantCalculator(val repository: TipCalculationRepository = TipCalculationRepository())
{
    fun calculateTip(checkInput: Double, tipPctInput: Int): TipCalculation {

        val tipAmount = (checkInput * (tipPctInput.toDouble() / 100.0))
                .toBigDecimal()
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()

        var grandTotal = checkInput + tipAmount

        return TipCalculation(
                checkAmount = checkInput,
                tipPct = tipPctInput,
                tipAmount = tipAmount,
                grandTotal = grandTotal
        )
    }

    fun saveTipCalculation(tc: TipCalculation)
    {
        repository.saveTipCalculation(tc)
    }

    fun loadTipCalculationByName(locationName: String) : TipCalculation?
    {
        return repository.getTipCalculationByName(locationName)
    }

    fun loadSavedTipCalculations() : LiveData<List<TipCalculation>>
    {
        return repository.loadSavedTipCalculations()
    }
}