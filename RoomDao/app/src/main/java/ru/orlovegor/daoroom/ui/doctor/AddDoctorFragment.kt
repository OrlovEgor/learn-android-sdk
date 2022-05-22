package ru.orlovegor.daoroom.ui.doctor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.databinding.FragmentAddDoctorBinding
import ru.orlovegor.daoroom.ui.models.DoctorRW
import ru.orlovegor.daoroom.utils.toastFragmentResId

class AddDoctorFragment : Fragment(R.layout.fragment_add_doctor) {

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            checkState()
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private val binding: FragmentAddDoctorBinding by viewBinding()
    private val viewModel: AddDoctorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelState()
        viewModel.getSpecializations()
        viewModel.getCabinetsNumber()
        viewModel.getHospitalsName()
        viewModel.getWorkTimes()
        addWatcher(textWatcher)
        binding.addDoctorButton.setOnClickListener {
            viewModel.insertDoctor(createDoctor())
        }
    }

    private fun checkState() {
        binding.addDoctorButton.isEnabled =
            with(binding) {
                addDoctorFirstNameEdittext.text.isNotBlank() &&
                        addDoctorLastNameEdittext.text.isNotBlank() &&
                        addDoctorHospitalTextInput.text.isNotBlank() &&
                        addDoctorOpeningHoursTextInput.text.isNotBlank() &&
                        addDoctorSpecializationTextInput.text.isNotBlank() &&
                        addDoctorCabinetTextInput.text.isNotBlank()
            }
    }

    private fun addWatcher(watcher: TextWatcher) =
        with(binding) {
            addDoctorFirstNameEdittext.addTextChangedListener(watcher)
            addDoctorLastNameEdittext.addTextChangedListener(watcher)
            addDoctorHospitalTextInput.addTextChangedListener(watcher)
            addDoctorOpeningHoursTextInput.addTextChangedListener(watcher)
            addDoctorSpecializationTextInput.addTextChangedListener(watcher)
            addDoctorCabinetTextInput.addTextChangedListener(watcher)
        }


    private fun createDoctor() =
        arrayListOf(
            DoctorRW(
                id = 0,
                firstName = binding.addDoctorFirstNameEdittext.text.toString(),
                lastName = binding.addDoctorLastNameEdittext.text.toString(),
                hospitalName = binding.addDoctorHospitalTextInput.text.toString(),
                workTime = binding.addDoctorOpeningHoursTextInput.text.toString(),
                specialization = binding.addDoctorSpecializationTextInput.text.toString(),
                cabinetNumber = binding.addDoctorCabinetTextInput.text.toString().toInt()
            )
        )


    private fun observeViewModelState() {
        viewModel.toast.observe(viewLifecycleOwner, { toastFragmentResId(it) })
        viewModel.liveDataSpecialization.observe(viewLifecycleOwner,
            { specialization -> initSpecializationDropdown(specialization) })
        viewModel.liveDataCabinets.observe(viewLifecycleOwner, { cabinets ->
            initCabinetsDropdown(cabinets)
        })
        viewModel.liveDataHospitalsName.observe(
            viewLifecycleOwner,
            { hospitalsName -> initHospitalNameDropdown(hospitalsName) })
        viewModel.liveDataWorkTime.observe(viewLifecycleOwner,
            { workTimes -> initWorkTimesDropdown(workTimes) })
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner,
            { findNavController().popBackStack() })
    }

    private fun initWorkTimesDropdown(workTimesDropdown: ArrayList<String>) {
        val workTimesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            workTimesDropdown
        )
        binding.addDoctorOpeningHoursTextInput.setAdapter(workTimesAdapter)
    }

    private fun initHospitalNameDropdown(hospitalsName: ArrayList<String>) {
        val hospitalsNameAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            hospitalsName
        )
        binding.addDoctorHospitalTextInput.setAdapter(hospitalsNameAdapter)
    }

    private fun initCabinetsDropdown(cabinets: ArrayList<String>) {
        val cabinetAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            cabinets
        )
        binding.addDoctorCabinetTextInput.setAdapter(cabinetAdapter)
    }

    private fun initSpecializationDropdown(specializations: ArrayList<String>) {
        val specializationAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            specializations
        )
        binding.addDoctorSpecializationTextInput.setAdapter(specializationAdapter)
    }
}