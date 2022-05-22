package ru.orlovegor.contentprovider.download

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.orlovegor.contentprovider.R
import ru.orlovegor.contentprovider.data.DownloadRepository
import ru.orlovegor.contentprovider.utils.SingleLiveEvent

class DownloadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DownloadRepository(application)

    private val toastEvent = SingleLiveEvent<Int>()

    val toast: LiveData<Int>
        get() = toastEvent

    fun getFile() {
        viewModelScope.launch {
            try {
                repository.download(repository.createFile())
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }

    fun sendFile() {
        viewModelScope.launch {
            try {
                repository.shareFile()
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }
}