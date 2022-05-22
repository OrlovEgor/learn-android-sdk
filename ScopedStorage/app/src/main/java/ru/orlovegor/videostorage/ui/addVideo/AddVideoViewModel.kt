package ru.orlovegor.videostorage.ui.addVideo

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.orlovegor.videostorage.R
import ru.orlovegor.videostorage.data.VideoRepository
import ru.orlovegor.videostorage.utils.SingleLiveEvent

class AddVideoViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = VideoRepository(application)

    private val loadingState = SingleLiveEvent<Boolean>()
    private val toastEvent = SingleLiveEvent<Int>()

    val isLoading: LiveData<Boolean>
        get() = loadingState

    val toast: LiveData<Int>
        get() = toastEvent

    fun newSaveVideo(uri: Uri?, name: String, url: String){
        viewModelScope.launch {
            try {
                loadingState.postValue(true)
                    repo.saveVideo(uri, name, url)
            }catch (t:Throwable){
                toastEvent.postValue(R.string.error_toast)
                Log.d("Tag", "saveVideo Error ${t.message}")
            }finally {
                loadingState.postValue(false)
            }
        }
    }
}