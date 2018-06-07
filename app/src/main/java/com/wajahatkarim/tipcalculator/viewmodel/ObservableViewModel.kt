package com.wajahatkarim.tipcalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import com.android.databinding.library.baseAdapters.BR

abstract class ObservableViewModel(app: Application) : AndroidViewModel(app), Observable
{
    @delegate:Transient
    private val mCallbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.remove(callback)
    }

    fun notifyChange()
    {
        mCallbacks.notifyChange(this, BR._all)
    }
}