package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.graphics.component1
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.github.R
import com.skillbox.github.databinding.FragmentListRepositoryBinding
import com.skillbox.github.ui.main.MainFragmentDirections
import com.skillbox.github.utils.autoCleared
import com.skillbox.github.utils.toast

class RepositoriesListFragment : Fragment(R.layout.fragment_list_repository) {

    private val binding: FragmentListRepositoryBinding by viewBinding()
    private var repositoriesAdapter: RepositoriesAdapter by autoCleared()
    private val viewModel: RepositoriesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniList()
        bindViewModel()
    }

    private fun initList() {
        repositoriesAdapter = RepositoriesAdapter { avatarLink, name, owner ->
            navigateToDetailInfoFragment(avatarLink, name, owner)
            Log.d("CLICK LIST ITEM", "NavigateTo")
        }
        with(binding.listRepository) {
            adapter = repositoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun isLoadingState(state: Boolean) {
        binding.repositoryProgressBar.isVisible = state
    }

    private fun bindViewModel() {
        viewModel.repositoryList.observe(viewLifecycleOwner,
            Observer { repositoriesAdapter.items = it })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoadingState(it) })
        binding.repositorySearchButton.setOnClickListener { viewModel.search() }
        viewModel.showToast.observe(viewLifecycleOwner)
        { toast(requireContext(), getString(R.string.error)) }
    }

    private fun navigateToDetailInfoFragment(avatarLink: String, name: String, owner: String) {
        findNavController().navigate(
            RepositoriesListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailInformationFragment(
                avatarLink,
                name,
                owner
            )
        )
    }
}