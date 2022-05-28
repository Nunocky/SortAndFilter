package com.example.sortandfilter

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.sortandfilter.databinding.FragmentFilterDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterDialogViewModel @Inject constructor() : ViewModel() {

}

@AndroidEntryPoint
class FilterDialogFragment : DialogFragment() {
    private val viewModel: FilterDialogViewModel by viewModels()
    private lateinit var binding: FragmentFilterDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        binding = FragmentFilterDialogBinding.inflate(requireActivity().layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnOk.setOnClickListener {
            setFragmentResult(
                "filter",
                bundleOf("message" to "result from filter")
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
