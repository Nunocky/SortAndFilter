package com.example.sortandfilter

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.example.sortandfilter.databinding.FragmentSortDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SortDialogViewModel @Inject constructor() : ViewModel()

@AndroidEntryPoint
class SortDialogFragment : DialogFragment() {
    private val args: SortDialogFragmentArgs by navArgs()
    private val viewModel: SortDialogViewModel by viewModels()
    private lateinit var binding: FragmentSortDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        binding = FragmentSortDialogBinding.inflate(requireActivity().layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnOk.setOnClickListener {
            val sortField = if (binding.radioButton1.isChecked) 0 else 1
            val sortOrder = if (binding.radioButton3.isChecked) 0 else 1

            val sortParam = SortParam(sortField, sortOrder)
            setFragmentResult(
                "sort",
                bundleOf(
                    "sortParam" to sortParam
                )
            )
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        if (args.param.field == 0) {
            binding.radioButton1.isChecked = true
        } else {
            binding.radioButton2.isChecked = true
        }

        if (args.param.order == 0) {
            binding.radioButton3.isChecked = true
        } else {
            binding.radioButton4.isChecked = true
        }

        dialog.setContentView(binding.root)
        return dialog
    }
}