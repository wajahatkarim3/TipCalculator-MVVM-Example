package com.wajahatkarim.tipcalculator.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.wajahatkarim.tipcalculator.R
import com.wajahatkarim.tipcalculator.databinding.ActivityMainBinding
import com.wajahatkarim.tipcalculator.viewmodel.CalculatorViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SaveDialogFragment.Callback, LoadDialogFragment.Callback {

    lateinit var bi: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)

        //bi.vm = CalculatorViewModel(application)
        bi.vm = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
    }

    override fun onSaveTip(name: String) {
        bi.vm?.saveCurrentTip(name)
        Snackbar.make(bi.root, "Saved $name", Snackbar.LENGTH_SHORT).show()
    }

    override fun onTipSelected(name: String) {
        bi.vm?.loadTipCalculation(name)
        Snackbar.make(bi.root, "Loaded $name", Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_save -> {
                showSaveDialog()
                true
            }
            R.id.action_load -> {
                showLoadDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showSaveDialog()
    {
        val saveDialogFragment = SaveDialogFragment()
        saveDialogFragment.show(supportFragmentManager, "SaveDialog")
    }

    fun showLoadDialog()
    {
        val saveDialogFragment = LoadDialogFragment()
        saveDialogFragment.show(supportFragmentManager, "LoadDialog")
    }
}
