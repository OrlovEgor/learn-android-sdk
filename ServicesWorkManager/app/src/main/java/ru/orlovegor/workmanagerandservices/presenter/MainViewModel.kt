package ru.orlovegor.workmanagerandservices.presenter

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.orlovegor.workmanagerandservices.data.Repository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = Repository(application)

    private val _toast = MutableSharedFlow<Int>()
    private val _error = MutableStateFlow(false)
    private val _progress = MutableStateFlow(false)

    val toast = _toast.asSharedFlow()
    val error = _error.asStateFlow()
    val progress = _progress.asStateFlow()

    fun downloadFile(uri: String){
        val status =   repo.startDownload(uri).asFlow()
        viewModelScope.launch {
            status.onEach{ workInfo ->
                when(workInfo.first().state){
                    WorkInfo.State.RUNNING -> Log.d("TAG", "RUNNING")
                    WorkInfo.State.BLOCKED -> Log.d("TAG", "BLOCKED")
                    WorkInfo.State.CANCELLED -> Log.d("TAG", "CANCELED")
                    WorkInfo.State.ENQUEUED -> Log.d("TAG", "ENQUEUED")
                    WorkInfo.State.SUCCEEDED -> Log.d("TAG", "SUCCEEDED")
                    WorkInfo.State.FAILED ->Log.d("TAG", "FAILED")
                }
            }
        }
    }
    fun stopDownload(){
        repo.stopDownload()
    }
}