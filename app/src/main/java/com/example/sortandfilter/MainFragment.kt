package com.example.sortandfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sortandfilter.data.SortParam
import com.example.sortandfilter.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    private val allItemsAdapter = ItemListAdapter()

    @Inject
    lateinit var preference: PreferenceRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerView.adapter = allItemsAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewModel.allItems.observe(viewLifecycleOwner) { list ->
            allItemsAdapter.submitList(list)
        }
        // allItemsを flowのまま使うとき
//        lifecycleScope.launchWhenStarted {
//            viewModel.allItems.collect { list ->
//                allItemsAdapter.submitList(list)
//            }
//        }

        binding.btnAddItems.setOnClickListener {
            viewModel.addItems()
        }

        binding.btnSort.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragmentToSortDialogFragment(preference.sortParam)
            findNavController().navigate(action)
        }

        binding.btnFilter.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragmentToFilterDialogFragment(preference.filterText)
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ソートダイアログの結果を受信 -> ViewModelへ渡す
        setFragmentResultListener("sort") { _, bundle ->
            val sortParam = bundle["sortParam"] as SortParam? ?: SortParam()
            preference.sortParam = sortParam
            viewModel.sortParam.value = sortParam
        }

        // フィルタダイアログの結果を受信 -> ViewModelへ渡す
        setFragmentResultListener("filter") { _, bundle ->
            val filterText = bundle["filterText"] as String? ?: ""
            preference.filterText = filterText
            viewModel.filterText.value = filterText
        }
    }
}