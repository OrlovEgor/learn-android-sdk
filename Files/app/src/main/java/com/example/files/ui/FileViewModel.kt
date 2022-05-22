package com.example.files.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.files.R
import com.example.files.repository.FileRepository
import com.example.files.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class FileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FileRepository(application)
    private val scope = viewModelScope

    private val showSuccessToastLiveData = SingleLiveEvent<Int>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val showSuccessToast: LiveData<Int>
        get() = showSuccessToastLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getFile(url: String) {
        scope.launch {
            var downloadedFile: File? = null
            Log.d("app", "file name = ${downloadedFile?.name}")
            if (repository.checkDownloadedFile(url)) {
                withContext(Dispatchers.Main) {
                    makeToast(R.string.already_downloaded_toast)
                }
            } else {
                isLoadingLiveData.postValue(true)
                try {
                    downloadedFile = repository.createFile(url)
                    repository.download(downloadedFile, url)
                    repository.saveToSharedPrefs(downloadedFile, url)
                    withContext(Dispatchers.Main) {

                        makeToast(R.string.file_downloaded)
                    }

                } catch (t: IOException) {
                    Log.d("app", "message error = ${t.message}")
                    downloadedFile?.let { repository.deleteFile(it) }
                    isLoadingLiveData.postValue(false)
                } finally {
                    isLoadingLiveData.postValue(false)
                }
            }
        }
    }

    fun isFirstLaunch() {
        scope.launch {
            try {
                if (!repository.isFirstLaunch()) {
                    val url = repository.getUrlFromAssets()
                    val file = repository.createFile(url)
                    repository.download(file, url)
                    repository.addFlagToFirsLaunch()
                }
            } catch (t: Throwable) {
                // TODO можно как нибудь обработаь стостояние ошибки при загрузке,может повторять ф ию рекурсивно.
                // TODO или выводить Toast или Snack о том что бы пользователь проверит интернет соединение.
            }
        }
    }

    private fun makeToast(id: Int) {
        showSuccessToastLiveData.postValue(id)
    }
}