package com.example.sortandfilter

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.example.sortandfilter.databinding.FragmentFilterDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterDialogViewModel @Inject constructor() : ViewModel()

@AndroidEntryPoint
class FilterDialogFragment : DialogFragment() {
    private val args: FilterDialogFragmentArgs by navArgs()
    private val viewModel: FilterDialogViewModel by viewModels()
    private lateinit var binding: FragmentFilterDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        binding = FragmentFilterDialogBinding.inflate(requireActivity().layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnOk.setOnClickListener {

            val filterText = when {
                binding.radioButton2.isChecked -> {
                    "ABC"
                }
                binding.radioButton3.isChecked -> {
                    "XYZ"
                }
                else -> {
                    ""
                }
            }

            setFragmentResult(
                "filter",
                bundleOf(
                    "filterText" to filterText
                )
            )
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        when (args.text) {
            "ABC" -> binding.radioButton2.isChecked = true
            "XYZ" -> binding.radioButton3.isChecked = true
            else -> binding.radioButton1.isChecked = true
        }

        dialog.setContentView(binding.root)
        return dialog
    }
}
