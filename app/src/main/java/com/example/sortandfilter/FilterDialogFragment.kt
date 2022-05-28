package com.example.sortandfilter

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
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

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentFilterDialogBinding.inflate(inflater, container, false)
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = viewLifecycleOwner
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)

        val dialog = Dialog(requireContext())
        // ダイアログの背景を透過にする
        //dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Bundleに渡した値を取り出す
//        arguments?.let {
//            title = it.getString("TitleKey", "")
//            message = it.getString("MesasgeKey", "")
//        }
//        val binding = LayoutCustomDialogBinding.inflate(requireActivity().layoutInflater)
//        binding.title.text = title
//        binding.message.text = message
//        binding.positiveButton.text = "OK"
//        binding.negativeButton.text = "キャンセル"

        binding = FragmentFilterDialogBinding.inflate(requireActivity().layoutInflater)
        binding.viewModel = viewModel
//        binding.lifecycleOwner = viewLifecycleOwner
        dialog.setContentView(binding.root)
        return dialog
    }
}
