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
import androidx.navigation.fragment.findNavController
import com.example.sortandfilter.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val sortCondition = MutableStateFlow(0)
    val sortOrder = MutableStateFlow(0)
    val filterText = MutableStateFlow("")
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

        setFragmentResultListener("sort") { requestKey, bundle ->
            Log.d(TAG, requestKey)
            Log.d(TAG, bundle["message"] as String? ?: "")

            viewModel.sortCondition.value = 0
            viewModel.sortOrder.value = 0
        }

        setFragmentResultListener("filter") { requestKey, bundle ->
            Log.d(TAG, requestKey)
            Log.d(TAG, bundle["message"] as String? ?: "")

            viewModel.filterText.value = ""
        }
    }

    companion object {
        private const val TAG = "MainFragment"
    }
}