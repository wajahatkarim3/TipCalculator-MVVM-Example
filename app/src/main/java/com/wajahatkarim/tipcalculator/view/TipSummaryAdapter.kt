package com.wajahatkarim.tipcalculator.view

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wajahatkarim.tipcalculator.R
import com.wajahatkarim.tipcalculator.databinding.LoadListItemLayoutBinding
import com.wajahatkarim.tipcalculator.viewmodel.TipCalcSummaryItem

class TipSummaryAdapter(val onItemSelected: (item: TipCalcSummaryItem) -> Unit) : RecyclerView.Adapter<TipSummaryAdapter.TipSummaryViewHolder>() {

    private val tipCalculationSummaries = mutableListOf<TipCalcSummaryItem>()

    fun updateList(updates: List<TipCalcSummaryItem>)
    {
        tipCalculationSummaries.clear()
        tipCalculationSummaries.addAll(updates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipSummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bi = DataBindingUtil.inflate<LoadListItemLayoutBinding>(inflater, R.layout.load_list_item_layout, parent, false)
        return TipSummaryViewHolder(bi)
    }

    override fun getItemCount(): Int {
        return tipCalculationSummaries.size
    }

    override fun onBindViewHolder(holder: TipSummaryViewHolder, position: Int) {
        holder.bind(tipCalculationSummaries[position])
    }

    inner class TipSummaryViewHolder(val binding: LoadListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: TipCalcSummaryItem)
        {
            binding.item = item
            binding.root.setOnClickListener {
                onItemSelected(item)
            }
            binding.executePendingBindings()
        }
    }
}