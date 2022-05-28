package com.example.sortandfilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.sortandfilter.database.Item
import com.example.sortandfilter.database.ItemRepository
import com.example.sortandfilter.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
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

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnAddItems.setOnClickListener {
            viewModel.addItems()
        }

        binding.btnSort.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sortDialogFragment)
        }

        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filterDialogFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ソートダイアログの結果を受信
        setFragmentResultListener("sort") { requestKey, bundle ->
            Log.d(TAG, requestKey)

            val sortBy = bundle["sortBy"] as Int? ?: 0
            val sortOrder = bundle["sortOrder"] as Int? ?: 0
            Log.d(TAG, "sortBy = $sortBy")
            Log.d(TAG, "sortOrder = $sortOrder")

            viewModel.sortCondition.value = sortBy
            viewModel.sortOrder.value = sortOrder
        }

        // フィルタダイアログの結果を受信
        setFragmentResultListener("filter") { requestKey, bundle ->
            Log.d(TAG, requestKey)
            val filterText = bundle["filterText"] as String? ?: ""
            Log.d(TAG, "filterText = $filterText")

            viewModel.filterText.value = filterText
        }
    }

    companion object {
        private const val TAG = "MainFragment"
    }
}