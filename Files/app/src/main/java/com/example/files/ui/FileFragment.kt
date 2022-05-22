package com.example.files.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.files.R
import com.example.files.databinding.FragmentFileBinding
import com.example.files.utils.toast

class FileFragment : Fragment(R.layout.fragment_file) {

    private val binding: FragmentFileBinding by viewBinding()
    private val viewModel by viewModels<FileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelState()
        setDownloadButton()
        viewModel.isFirstLaunch()
    }

    private fun setDownloadButton() {
        val uri = binding.fileEditText.text.toString()
        if (uri.isNotEmpty()) {
            binding.fileDownloadButton.setOnClickListener {
                viewModel.getFile(uri)
            }
        }
    }

    private fun observeViewModelState() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { state -> loading(state) })
        viewModel.showSuccessToast.observe(viewLifecycleOwner, { stringId -> toast(stringId)})
    }

    private fun loading(state: Boolean) {
        binding.fileProgressBar.isVisible = state
        binding.fileDownloadButton.isActivated = !state
        binding.fileEditText.isActivated = !state
    }
}