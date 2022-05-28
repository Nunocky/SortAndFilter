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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        binding = FragmentSortDialogBinding.inflate(requireActivity().layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnOk.setOnClickListener {
            setFragmentResult(
                "sort",
                bundleOf("message" to "result from sort")
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