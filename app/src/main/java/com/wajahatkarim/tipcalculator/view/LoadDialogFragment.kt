package com.wajahatkarim.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.wajahatkarim.tipcalculator.R
import com.wajahatkarim.tipcalculator.viewmodel.CalculatorViewModel

class LoadDialogFragment : DialogFragment() {

    interface Callback {
        fun onTipSelected(name: String)
    }

    private var loadTipCallback: Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loadTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        loadTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val saveDailog = context?.let { ctx ->
            AlertDialog.Builder(ctx)
                    .setView(createView(ctx))
                    .setNegativeButton("Cancel", null)
                    .create()
        }

        return saveDailog!!
    }

    fun createView(ctx: Context) : View
    {
        val rv = LayoutInflater
                .from(ctx)
                .inflate(R.layout.load_dialog_layout, null)
                .findViewById<RecyclerView>(R.id.recyclerLoadList)

        rv.setHasFixedSize(true)
        rv.addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))

        val adapter = TipSummaryAdapter {
            loadTipCallback?.onTipSelected(it.locationName)
            dismiss()
        }

        rv.adapter = adapter

        val vm = ViewModelProviders.of(activity!!).get(CalculatorViewModel::class.java)
        vm.loadSavedTipCalSummaries().observe(this, Observer{
            if (it != null)
                adapter.updateList(it)
        })

        return rv
    }

}