package com.mask.compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mask.compose.vo.ListItemVo

/**
 * ListViewModel
 *
 * Create by lishilin on 2025-04-30
 */
class ListViewModel : ViewModel() {

    private val _itemList = MutableLiveData<List<ListItemVo?>?>(emptyList())
    val itemList: LiveData<List<ListItemVo?>?> = _itemList

    val totalPrice: LiveData<Int> = _itemList.map { dataList ->
        dataList?.sumOf { data ->
            data?.totalPrice ?: 0
        } ?: 0
    }

    init {
        val dataList = mutableListOf<ListItemVo?>()
        for (i in 0 until 100) {
            val data = createItem(i)
            dataList.add(0, data)
        }
        _itemList.value = dataList
    }

    private fun createItem(lastIdInt: Int): ListItemVo {
        val curIdInt = lastIdInt + 1
        return ListItemVo(curIdInt.toString(), "商品_$curIdInt", curIdInt * 10, 1)
    }

    fun addItem() {
        val lastIdInt = _itemList.value?.getOrNull(0)?.id?.toInt() ?: 0
        val data = createItem(lastIdInt)

        val dataList = _itemList.value?.toMutableList() ?: mutableListOf()
        dataList.add(0, data)
        _itemList.value = dataList
    }

    fun deleteItem(id: String?) {
        if (id.isNullOrEmpty()) {
            return
        }
        _itemList.value = _itemList.value?.filterNot { data ->
            id == data?.id
        } ?: emptyList()
    }

    fun addQuantity(id: String?) {
        if (id.isNullOrEmpty()) {
            return
        }
        val data = _itemList.value?.firstOrNull { data ->
            id == data?.id
        }
        val quantity = data?.quantity?.plus(1) ?: 0
        updateQuantity(id, quantity)
    }

    fun minusQuantity(id: String?) {
        if (id.isNullOrEmpty()) {
            return
        }
        val data = _itemList.value?.firstOrNull { data ->
            id == data?.id
        }
        val quantity = data?.quantity?.minus(1) ?: 0
        updateQuantity(id, quantity)
    }

    private fun updateQuantity(id: String, quantity: Int) {
        if (quantity < 1) {
            deleteItem(id)
            return
        }
        _itemList.value = _itemList.value?.map { data ->
            if (id == data?.id) {
                data.copy(quantity = quantity)
            } else {
                data
            }
        } ?: emptyList()
    }

}