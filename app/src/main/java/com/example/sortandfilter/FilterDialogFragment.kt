package com.example.sortandfilter

import android.app.Dialog
import android.os.Bundle
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

    override fun onResume() {
        super.onResume()

        // TODO 呼び出し時の値をセットする (safe args使用)
        binding.radioButton1.isChecked = true
    }

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

        dialog.setContentView(binding.root)
        return dialog
    }
}
