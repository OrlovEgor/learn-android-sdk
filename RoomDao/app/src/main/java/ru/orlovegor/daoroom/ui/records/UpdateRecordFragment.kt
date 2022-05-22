package ru.orlovegor.daoroom.ui.records

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.databinding.FragmentUpdateRecordBinding
import ru.orlovegor.daoroom.utils.toastFragmentResId


class UpdateRecordFragment : Fragment(R.layout.fragment_update_record) {
    private val binding: FragmentUpdateRecordBinding by viewBinding()
    private val args by navArgs<UpdateRecordFragmentArgs>()
    private val viewModel: UpdateRecordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableViewModelState()
        binding.updateRecordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.updateRecordButton.isEnabled =
                    binding.updateRecordEditText.text.isNotBlank()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.updateRecordButton.setOnClickListener {
            viewModel.updateDiagnosis(binding.updateRecordEditText.text.toString(), args.recordId)
        }
    }

    private fun observableViewModelState() {
        viewModel.successEvent.observe(viewLifecycleOwner, { findNavController().popBackStack() })
        viewModel.toast.observe(viewLifecycleOwner, { toastFragmentResId(it) })
    }
}