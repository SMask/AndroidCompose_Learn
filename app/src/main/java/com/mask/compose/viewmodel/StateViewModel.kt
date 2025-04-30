package com.mask.compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * StateViewModel
 *
 * Create by lishilin on 2025-04-30
 */
class StateViewModel : ViewModel() {

    // 加
    private val _countAdd = MutableLiveData(0)
    val countAdd: LiveData<Int> = _countAdd

    // 减
    private val _countMinus = MutableLiveData(0)
    val countMinus: LiveData<Int> = _countMinus

    /**
     * 加
     */
    fun addCount() {
        _countAdd.value = (_countAdd.value ?: 0).plus(1)
    }

    /**
     * 减
     */
    fun minusCount() {
        _countMinus.value = (_countMinus.value ?: 0).minus(1)
    }
}