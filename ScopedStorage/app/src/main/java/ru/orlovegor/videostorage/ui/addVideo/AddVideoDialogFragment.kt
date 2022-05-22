package ru.orlovegor.videostorage.ui.addVideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.orlovegor.videostorage.databinding.DialogAddVideoBinding
import ru.orlovegor.videostorage.utils.getMIMEType
import ru.orlovegor.videostorage.utils.toastFragmentResId

class AddVideoDialogFragment : BottomSheetDialogFragment() {

    private val binding: DialogAddVideoBinding by viewBinding()
    private val viewModel: AddVideoViewModel by viewModels()

    private lateinit var createVideoLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogAddVideoBinding.inflate(LayoutInflater.from(requireContext())).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableViewModelState()
        initCreateVideoLauncher()
        binding.dialogDownloadButton.setOnClickListener {
            if (binding.dialogEdittextVideoName.text.isNotEmpty() &&
                binding.dialogEdittextVideoUrl.text.isNotEmpty()
            ) {
                startDownloadVideo(
                    binding.dialogEdittextVideoUrl.text.toString(),
                    binding.dialogEdittextVideoName.text.toString()

                )
            } else Toast.makeText(
                requireContext(),
                "Invalid or empty name text",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun observableViewModelState() {
        viewModel.toast.observe(viewLifecycleOwner) { resId ->
            toastFragmentResId(resId)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.addVideoProgressBar.isVisible = isVisible
        }
    }

    private fun initCreateVideoLauncher() {
        createVideoLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument()
        ) { uri ->
            viewModel.newSaveVideo(
                uri,
                binding.dialogEdittextVideoName.text.toString(),
                binding.dialogEdittextVideoUrl.text.toString()
            )
        }
    }

    private fun startDownloadVideo(url: String, name: String) {
        val newName = name + "." + getMIMEType(url)?.substringAfterLast("/")
        createVideoLauncher.launch(newName)
    }

}