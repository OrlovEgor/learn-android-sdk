package ru.orlovegor.daoroom.ui.doctor

import android.app.Fragment
import android.os.Bundle

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.databinding.FragmentDoctorBinding
import ru.orlovegor.daoroom.ui.adapters.DoctorAdapter
import ru.orlovegor.daoroom.utils.autoCleared
import ru.orlovegor.daoroom.utils.toastFragmentResId

class DoctorFragment : androidx.fragment.app.Fragment(R.layout.fragment_doctor) {

    private val binding: FragmentDoctorBinding by viewBinding()
    private var doctorAdapter: DoctorAdapter by autoCleared()
    private val viewModel: DoctorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isFirsLaunch()
        viewModel.getDoctor()
        observeViewModelState()
        initList()
        initFabButton()
    }

    private fun initList() {
        doctorAdapter = DoctorAdapter({ doctorId ->
            findNavController(this).navigate(
                DoctorFragmentDirections.actionDoctorFragmentToPatientFragment(doctorId)
            )
        }, { doctorId -> viewModel.deleteDoctor(doctorId) })
        with(binding.listDoctors) {
            adapter = doctorAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModelState() {
        viewModel.doctorLiveData.observe(
            viewLifecycleOwner, { listDoctor -> doctorAdapter.submitList(listDoctor) })
        viewModel.toast.observe(viewLifecycleOwner, { toastResId -> toastFragmentResId(toastResId) })
    }

    private fun initFabButton() {
        binding.createDoctorFabButton.setOnClickListener {
            findNavController().navigate(DoctorFragmentDirections.actionDoctorFragmentToAddDoctorFragment())
        }
    }
}



