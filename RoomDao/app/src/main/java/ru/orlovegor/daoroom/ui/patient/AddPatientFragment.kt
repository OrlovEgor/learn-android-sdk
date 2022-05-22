package ru.orlovegor.daoroom.ui.patient

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.databinding.FragmentAddPatientBinding
import ru.orlovegor.daoroom.utils.toastFragmentResId

class AddPatientFragment : Fragment(R.layout.fragment_add_patient) {

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            checkState()
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private val binding: FragmentAddPatientBinding by viewBinding()
    private val viewModel = AddPatientViewModel()
    private val args by navArgs<AddPatientFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners(textWatcher)
        observeViewModelState()
        binding.addDiagnosisButton.setOnClickListener {
            viewModel.addPatient(
                args.doctorId,
                binding.addPatientFirstNameEdittext.text.toString(),
                binding.addPatientLastNameEdittext.text.toString()
            )
        }
    }

    private fun observeViewModelState() {
        viewModel.eventSuccessful.observe(viewLifecycleOwner,
            { findNavController().popBackStack() })
        viewModel.toast.observe(viewLifecycleOwner, { toastFragmentResId(it) })
    }

    private fun initListeners(textWatcher: TextWatcher) {
        with(binding) {
            addPatientFirstNameEdittext.addTextChangedListener(textWatcher)
            addPatientLastNameEdittext.addTextChangedListener(textWatcher)
        }
    }

    private fun checkState() {
        binding.addDiagnosisButton.isEnabled =
            binding.addPatientFirstNameEdittext.text.isNotBlank() &&
                    binding.addPatientLastNameEdittext.text.isNotBlank()
    }
}