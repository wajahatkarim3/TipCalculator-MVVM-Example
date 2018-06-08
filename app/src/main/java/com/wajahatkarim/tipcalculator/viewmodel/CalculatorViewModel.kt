package com.wajahatkarim.tipcalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.databinding.BaseObservable
import com.wajahatkarim.tipcalculator.R
import com.wajahatkarim.tipcalculator.model.RestaurantCalculator
import com.wajahatkarim.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(app: Application, val calculator: RestaurantCalculator = RestaurantCalculator()) : ObservableViewModel(app)
{
    private var lastTipCalculated = TipCalculation()

    var inputCheckAmount = ""

    var inputTipPercentage = ""

    val outputCheckAmount get() = getApplication<Application>().getString(R.string.dollar_amount, lastTipCalculated.checkAmount)
    val outputTipAmount get() = getApplication<Application>().getString(R.string.dollar_amount, lastTipCalculated.tipAmount)
    val outputTotalDollarAmount get() = getApplication<Application>().getString(R.string.dollar_amount, lastTipCalculated.grandTotal)
    val locationName get() = lastTipCalculated.locationName

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation)
    {
        lastTipCalculated = tc
        notifyChange()
    }

    fun calculateTip()
    {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPct = inputTipPercentage.toIntOrNull()

        if(checkAmount != null && tipPct != null) {
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
        }
    }

    fun loadSavedTipCalSummaries() : LiveData<List<TipCalcSummaryItem>>
    {
        return Transformations.map(calculator.loadSavedTipCalculations(), { tipCalsList ->
            tipCalsList.map {
                TipCalcSummaryItem(it.locationName, getApplication<Application>().getString(R.string.dollar_amount, it.grandTotal))
            }
        })
    }

    fun loadTipCalculation(name: String)
    {
        val tc = calculator.loadTipCalculationByName(name)

        if (tc != null) {
            inputCheckAmount = tc.checkAmount.toString()
            inputTipPercentage = tc.tipPct.toString()

            updateOutputs(tc)
            notifyChange()
        }
    }

    fun saveCurrentTip(name: String)
    {
        val tipToSave = lastTipCalculated.copy(locationName = name)
        calculator.saveTipCalculation(tipToSave)
        updateOutputs(tipToSave)
    }
}