package com.wajahatkarim.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText

class SaveDialogFragment : DialogFragment() {

    interface Callback {
        fun onSaveTip(name: String)
    }

    private var saveTipCallback: SaveDialogFragment.Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        saveTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        saveTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val saveDailog = context?.let { ctx ->

            val editText = EditText(ctx)
            editText.id = viewId
            editText.hint = "Enter Location"

            AlertDialog.Builder(ctx)
                    .setView(editText)
                    .setNegativeButton("Cancel", { _,_ ->
                        dismiss()
                    })
                    .setPositiveButton("Save", { _,_ ->
                        onSave(editText)
                    })
                    .create()
        }

        return saveDailog!!
    }

    private fun onSave(editText: EditText)
    {
        var text = editText.text.toString()
        if (!text.isEmpty())
        {
            saveTipCallback?.onSaveTip(text)
        }
    }

    companion object {
        val viewId = View.generateViewId()
    }
}