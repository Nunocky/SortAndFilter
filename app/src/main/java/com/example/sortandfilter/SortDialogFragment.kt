package com.example.sortandfilter

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.sortandfilter.databinding.FragmentSortDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SortDialogViewModel @Inject constructor() : ViewModel() {

}

@AndroidEntryPoint
class SortDialogFragment : DialogFragment() {
    private val viewModel: SortDialogViewModel by viewModels()
    private lateinit var binding: FragmentSortDialogBinding

    override fun onResume() {
        super.onResume()

        // TODO 呼び出し時の値をセットする (safe args使用)
        binding.radioButton1.isChecked = true
        binding.radioButton3.isChecked = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        binding = FragmentSortDialogBinding.inflate(requireActivity().layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnOk.setOnClickListener {
            val sortBy = if (binding.radioButton1.isChecked) 0 else 1
            val sortOrder = if (binding.radioButton3.isChecked) 0 else 1

            setFragmentResult(
                "sort",
                bundleOf(
                    "sortBy" to sortBy,
                    "sortOrder" to sortOrder
                )
            )
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        dialog.setContentView(binding.root)
        return dialog
    }
}