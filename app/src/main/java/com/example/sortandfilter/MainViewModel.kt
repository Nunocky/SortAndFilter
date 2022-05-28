package com.example.sortandfilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.sortandfilter.database.Item
import com.example.sortandfilter.database.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
    private val _allItems = itemRepository.findAll()

    val allItems = _allItems.asLiveData()

    val sortCondition = MutableStateFlow(0)
    val sortOrder = MutableStateFlow(0)
    val filterText = MutableStateFlow("")

    /**
     * Roomにデータを 10個追加する
     */
    fun addItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repeat(10) {
                val item = Item(
                    0, texts.random()
                )
                itemRepository.insert(item)
            }
        }
    }

    companion object {
        private val texts = listOf(
            "ABC",
            "ABC DEF",
            "ABC XYZ",
            "XYZ",
            "UVW XYZ "
        )
    }
}