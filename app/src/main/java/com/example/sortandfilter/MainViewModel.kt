package com.example.sortandfilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.sortandfilter.database.Item
import com.example.sortandfilter.database.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    preference: PreferenceRepository
) : ViewModel() {

    val sortParam = MutableStateFlow(preference.sortParam)
    val filterText = MutableStateFlow(preference.filterText)

    private val _allItems = itemRepository.findAllAsFlow()

    // _allItems, sortParam, filterParamの変化を受けてテキストフィルタとソートを実行する
//    val allItems = combine(_allItems, sortParam, filterText) { items, sortParam, filterText ->
//        if (sortParam.field == 0) {
//            if (sortParam.order == 0) {
//                items.sortedBy { it.id }
//            } else {
//                items.sortedBy { it.id }.reversed()
//            }
//        } else {
//            if (sortParam.order == 0) {
//                items.sortedBy { it.title }
//            } else {
//                items.sortedBy { it.title }.reversed()
//            }
//        }.filter {
//            it.title.contains(filterText)
//        }
//    }.asLiveData()

    val allItems = combine(_allItems, sortParam, filterText) { allItems, sortParam, filterText ->
        //val items = allItems
        // MEMO combineの中で Roomにアクセスするには withContextを使う必要がある
        var items = withContext(Dispatchers.IO) {
            itemRepository.findAll()
        }

        items = items.filter {
            it.title.contains(filterText)
        }

        items = if (sortParam.field == 0) {
            if (sortParam.order == 0) {
                items.sortedBy { it.id }
            } else {
                items.sortedBy { it.id }.reversed()
            }
        } else {
            if (sortParam.order == 0) {
                items.sortedBy { it.title }
            } else {
                items.sortedBy { it.title }.reversed()
            }
        }

        items
    }.asLiveData()

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