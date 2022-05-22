package ru.orlovegor.contentprovider.download

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.contentprovider.R
import ru.orlovegor.contentprovider.databinding.FragmentDownloadBinding
import ru.orlovegor.contentprovider.utils.toastFragment

class DownloadFragment : Fragment(R.layout.fragment_download) {

    private val binding: FragmentDownloadBinding by viewBinding()
    private val viewModel by viewModels<DownloadViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindShareButton()
        bindDownloadButton()
        observeViewModelState()
    }

    private fun bindDownloadButton() {
        binding.downloadFileDownload.setOnClickListener {
            viewModel.getFile()
        }
    }

    private fun bindShareButton() {
        binding.shareFileDownload.setOnClickListener {
            viewModel.sendFile()

        }
    }

    private fun observeViewModelState() {
        viewModel.toast.observe(viewLifecycleOwner, { resID -> toastFragment(resID) })
    }
}