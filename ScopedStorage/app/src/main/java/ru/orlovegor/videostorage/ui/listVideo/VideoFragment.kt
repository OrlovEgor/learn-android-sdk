package ru.orlovegor.videostorage.ui.listVideo

import android.Manifest
import android.app.Activity
import android.app.RemoteAction
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.videostorage.R
import ru.orlovegor.videostorage.databinding.FragmentVideoBinding
import ru.orlovegor.videostorage.ui.VideoAdapter
import ru.orlovegor.videostorage.utils.autoCleared
import ru.orlovegor.videostorage.utils.haveQ
import ru.orlovegor.videostorage.utils.toastFragmentResId

class VideoFragment : Fragment(R.layout.fragment_video) {

    private val binding: FragmentVideoBinding by viewBinding()
    private val viewModel: VideoViewModel by viewModels()

    private var videoAdapter: VideoAdapter by autoCleared()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var recoverableActionLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionResultListener()
        initRecoverableActionListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (hasPermission().not()) requestPermissions()
        initList()
        observableViewModelState()
        binding.addVideoFabButton.setOnClickListener {
            findNavController().navigate(VideoFragmentDirections.actionVideoFragmentToAddVideoDialogFragment())
        }
    }

    private fun hasPermission() = PERMISSIONS.all {
        ActivityCompat.checkSelfPermission(
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() =
        requestPermissionLauncher.launch(PERMISSIONS.toTypedArray())

    private fun initPermissionResultListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            if (permissionToGrantedMap.values.all { it }) {
                viewModel.permissionsGranted()
            } else {
                viewModel.permissionsDenied()
            }
        }
    }
    private fun updatePermissionUi(isGranted: Boolean){
        binding.addVideoFabButton.isVisible = isGranted
        binding.listVideo.isVisible = isGranted
    }

    private fun initRecoverableActionListener() {
        recoverableActionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            val isConfirmed = activityResult.resultCode == Activity.RESULT_OK
            if (isConfirmed)
                viewModel.confirmDelete()
            else
                viewModel.declineDelete()
        }
    }

    private fun initList() {
        videoAdapter = VideoAdapter { viewModel.deleteVideo(it) }
        with(binding.listVideo) {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun observableViewModelState() {
        viewModel.videoLiveData.observe(viewLifecycleOwner) { video ->
            videoAdapter.submitList(video)
        }
        viewModel.toastLiveData.observe(viewLifecycleOwner) { toastResId ->
            toastFragmentResId(toastResId)
        }
        viewModel.recoverableActionLiveData.observe(viewLifecycleOwner, ::handleRecoverableAction)

        viewModel.permissionsGrantedLiveData.observe(viewLifecycleOwner, ::updatePermissionUi)

    }

    private fun handleRecoverableAction(action: RemoteAction) {
        val request = IntentSenderRequest.Builder(action.actionIntent.intentSender).build()
        recoverableActionLauncher.launch(request)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePermissionState(hasPermission())
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() }
        )
    }
}