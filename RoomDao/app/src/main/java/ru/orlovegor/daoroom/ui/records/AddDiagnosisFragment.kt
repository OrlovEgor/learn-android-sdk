package ru.orlovegor.daoroom.ui.records

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.databinding.FragmentAddDiagnosisBinding
import ru.orlovegor.daoroom.utils.toastFragmentResId
import ru.orlovegor.daoroom.utils.viewModelsFactory

class AddDiagnosisFragment : Fragment(R.layout.fragment_add_diagnosis) {

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.addDiagnosisButton.isEnabled =
                binding.addDiagnosisEdittext.text.isNotBlank() &&
                        binding.addDiagnosisTherapyEdittext.text.isNotBlank()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    private val binding: FragmentAddDiagnosisBinding by viewBinding()
    private val args: AddDiagnosisFragmentArgs by navArgs()
    private val viewModel: RecordsViewModel by viewModelsFactory { RecordsViewModel(args.patientId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableViewModelState()
        addTextWatcher(textWatcher)
        binding.addDiagnosisButton.setOnClickListener {
            viewModel.addRecord(
                args.patientId,
                binding.addDiagnosisEdittext.text.toString(),
                binding.addDiagnosisTherapyEdittext.text.toString()
            )
        }
    }

    private fun addTextWatcher(watcher: TextWatcher) {
        binding.addDiagnosisEdittext.addTextChangedListener(watcher)
        binding.addDiagnosisTherapyEdittext.addTextChangedListener(watcher)
    }

    private fun observableViewModelState() {
        viewModel.successEvent.observe(viewLifecycleOwner, { findNavController().popBackStack() })
        viewModel.errorToast.observe(viewLifecycleOwner, { toastFragmentResId(it) })
    }
}