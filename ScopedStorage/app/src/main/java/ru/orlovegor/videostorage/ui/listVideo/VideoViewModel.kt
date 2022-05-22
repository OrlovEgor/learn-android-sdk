package ru.orlovegor.videostorage.ui.listVideo

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.orlovegor.videostorage.R
import ru.orlovegor.videostorage.data.Video
import ru.orlovegor.videostorage.data.VideoRepository
import ru.orlovegor.videostorage.utils.SingleLiveEvent
import ru.orlovegor.videostorage.utils.haveQ

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = VideoRepository(application)

    private val videoMutableLiveData = MutableLiveData<List<Video>>()
    private val toastSingleViveEvent = SingleLiveEvent<Int>()
    private val permissionsGrantedMutableLiveData = MutableLiveData(true)
    private val recoverableActionsMutableLiveData = MutableLiveData<RemoteAction>()
    private var isObservingStarted: Boolean = false


    private var pendingDeleteId: Long? = null

    val toastLiveData: LiveData<Int>
        get() = toastSingleViveEvent
    val videoLiveData: LiveData<List<Video>>
        get() = videoMutableLiveData
    val permissionsGrantedLiveData: LiveData<Boolean>
        get() = permissionsGrantedMutableLiveData
    val recoverableActionLiveData: LiveData<RemoteAction>
        get() = recoverableActionsMutableLiveData


    fun updatePermissionState(isGranted: Boolean) {
        if (isGranted) permissionsGranted()
        else permissionsDenied()
    }

    fun permissionsGranted() {
        getVideo()
        if (isObservingStarted.not())
            repo.observeVideo { getVideo() }
        isObservingStarted = true
        permissionsGrantedMutableLiveData.postValue(true)
    }

    fun permissionsDenied() {
        permissionsGrantedMutableLiveData.postValue(false)
    }

    private fun getVideo() {
        try {
            viewModelScope.launch {
                videoMutableLiveData.postValue(repo.getVideo())
                Log.d("Tag", "set live data view model get video Started")
            }
        } catch (t: Throwable) {
            videoMutableLiveData.postValue(emptyList())
            toastSingleViveEvent.postValue(R.string.error_toast)
            Log.d("Tag", "error view model get video Started")
        }
    }

    fun deleteVideo(id: Long) {
        viewModelScope.launch {
            try {
                repo.deleteVideo(id)
                pendingDeleteId = null
            } catch (t: Throwable) {
                if (haveQ() && t is RecoverableSecurityException) {
                    pendingDeleteId = id
                    recoverableActionsMutableLiveData.postValue(t.userAction)
                } else {
                    toastSingleViveEvent.postValue(R.string.error_toast)
                }
            }
        }
    }

    fun confirmDelete() {
        pendingDeleteId?.let {
            deleteVideo(it)
        }
    }

    fun declineDelete() {
        pendingDeleteId = null

    }

    override fun onCleared() {
        super.onCleared()
        repo.unregisterObserver()
    }
}