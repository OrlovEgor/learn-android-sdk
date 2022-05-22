package ru.orlovegor.daoroom.ui.records

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.databinding.FragmentRecordsBinding
import ru.orlovegor.daoroom.ui.adapters.RecordAdapter
import ru.orlovegor.daoroom.utils.autoCleared
import ru.orlovegor.daoroom.utils.toastFragmentResId
import ru.orlovegor.daoroom.utils.viewModelsFactory


class RecordsFragment : Fragment(R.layout.fragment_records) {

    private val binding: FragmentRecordsBinding by viewBinding()
    private val args: RecordsFragmentArgs by navArgs()
    private val viewModel: RecordsViewModel by viewModelsFactory { RecordsViewModel(args.patientId) }
    private var recordsAdapter: RecordAdapter by autoCleared()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observableViewModelState()
        binding.recordAddFab.setOnClickListener {
            findNavController().navigate(
                RecordsFragmentDirections.actionRecordsFragmentToAddDiagnosisFragment(
                    args.patientId
                )
            )
        }
    }

    private fun observableViewModelState() {
        viewModel.records.observe(viewLifecycleOwner, { recordsAdapter.submitList(it) })
        viewModel.errorToast.observe(viewLifecycleOwner, { toastFragmentResId(it) })
    }

    private fun initList() {
        recordsAdapter = RecordAdapter({findNavController().navigate(RecordsFragmentDirections.actionRecordsFragmentToUpdateRecordFragment(it))}, {})
        with(binding.listRecords) {
            adapter = recordsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}