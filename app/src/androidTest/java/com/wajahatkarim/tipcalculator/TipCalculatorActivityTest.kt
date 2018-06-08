package com.wajahatkarim.tipcalculator

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.wajahatkarim.tipcalculator.view.MainActivity
import org.junit.Rule
import org.junit.Test

class TipCalculatorActivityTest {

    @get:Rule var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testTipCalculator()
    {
        // Calculate Tip
        enter(checkAmount = 15.99, tipPercent = 15)
        calculateTip()
        assertOutput(name = "", checkAmount = "$15.99", tipAmount = "$2.40", total= "$18.39")

        // Save Tip
        saveTip(name = "BBQ Max")
        assertOutput(name = "BBQ Max", checkAmount = "$15.99", tipAmount = "$2.40", total= "$18.39")

        // Clear Outputs
        clearOutputs()
        assertOutput(name = "", checkAmount = "$0.00", tipAmount = "$0.00", total= "$0.00")

        // Load Tip
        loadTip(name = "BBQ Max")
        assertOutput(name = "BBQ Max", checkAmount = "$15.99", tipAmount = "$2.40", total= "$18.39")
    }

    private fun loadTip(name: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
        onView(withText("Load")).perform(click())
        onView(withText(name)).perform(click())
    }

    private fun clearOutputs() {
        enter(0.0, 0)
        calculateTip()
    }

    private fun saveTip(name: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
        onView(withText("Save")).perform(click())
        onView(withHint("Enter Location")).perform(replaceText(name))
        onView(withText("Save")).perform(click())
    }

    private fun assertOutput(name: String, checkAmount: String, tipAmount: String, total: String) {
        onView(withId(R.id.txtBillPayment)).check(matches(withText(checkAmount)))
        onView(withId(R.id.txtTipAmount)).check(matches(withText(tipAmount)))
        onView(withId(R.id.txtGrandTotal)).check(matches(withText(total)))
    }

    private fun calculateTip() {
        onView(withId(R.id.fab)).perform(click())
    }

    private fun enter(checkAmount: Double, tipPercent: Int) {
        onView(withId(R.id.txtCheckAmount)).perform(replaceText(checkAmount.toString()))
        onView(withId(R.id.txtTipPercentage)).perform(replaceText(tipPercent.toString()))
    }

}