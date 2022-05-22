package ru.orlovegor.daoroom.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.databinding.FragmentPatientBinding
import ru.orlovegor.daoroom.ui.adapters.PatientAdapter
import ru.orlovegor.daoroom.utils.autoCleared
import ru.orlovegor.daoroom.utils.toastFragmentResId
import ru.orlovegor.daoroom.utils.toastFragmentString
import ru.orlovegor.daoroom.utils.viewModelsFactory

class PatientFragment : Fragment(R.layout.fragment_patient) {

    private val binding: FragmentPatientBinding by viewBinding()
    private val args by navArgs<PatientFragmentArgs>()
    private var patientAdapter: PatientAdapter by autoCleared()
    private val viewModel by viewModelsFactory { PatientViewModel(args.doctorId) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableViewModelState()
        initList()
        binding.patientAddFab.setOnClickListener {
            findNavController().navigate(
                PatientFragmentDirections.actionPatientFragmentToAddPatientFragment(
                    args.doctorId
                )
            )
        }
    }

    private fun observableViewModelState() {
        viewModel.toast.observe(viewLifecycleOwner, { toastFragmentResId(it) })
        viewModel.patients.observe(viewLifecycleOwner,
            { patients -> patientAdapter.submitList(patients.patients) })
        viewModel.doctorsToast.observe(viewLifecycleOwner, { toastFragmentString(it) })
    }

    private fun popupMenu(patientId: Long) {
        val popupMenu = PopupMenu(requireContext(), binding.patientAddFab)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_action_get_doctors -> {
                    viewModel.getDoctorsByPatient(patientId)
                }
                R.id.menu_action_delete -> {
                    viewModel.deletePatient(patientId)
                }
            }
            true
        }
        popupMenu.show()
    }

    private fun initList() {
        patientAdapter = PatientAdapter({
            findNavController().navigate(
                PatientFragmentDirections.actionPatientFragmentToRecordsFragment(
                    it
                )
            )
        },
            { popupMenu(it) })
        with(binding.listPatients) {
            adapter = patientAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}