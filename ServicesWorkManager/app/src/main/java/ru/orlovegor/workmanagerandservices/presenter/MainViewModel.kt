package ru.orlovegor.workmanagerandservices.presenter

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import kotlinx.coroutines.flow.*
import ru.orlovegor.workmanagerandservices.R
import ru.orlovegor.workmanagerandservices.data.Repository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = Repository(application)

    private val _toast = MutableSharedFlow<Int>()
    private val _error = MutableStateFlow(false)
    private val _progress = MutableStateFlow(false)

    private val _workerStatus: Flow<MutableList<WorkInfo>> = repo.observeWork()

    val toast = _toast.asSharedFlow()
    val error = _error.asStateFlow()
    val progress = _progress.asStateFlow()

    init {
        check()
    }

    private fun check() {
        _workerStatus.map { listWorkInfo ->
            if (listWorkInfo.isNotEmpty())
                when (listWorkInfo.first().state) {
                    WorkInfo.State.RUNNING -> {
                        Log.d("TAG", "RUNNING")
                    }
                    WorkInfo.State.BLOCKED -> Log.d("TAG", "BLOCKED")
                    WorkInfo.State.CANCELLED -> {
                        Log.d("TAG", "CANCELED")
                        _progress.emit(false)
                        _error.emit(false)
                    }
                    WorkInfo.State.ENQUEUED -> {
                        Log.d("TAG", "ENQUEUED")
                        _progress.emit(true)
                    }
                    WorkInfo.State.SUCCEEDED -> {
                        Log.d("TAG", "SUCCEEDED")
                        _toast.emit(R.string.success)
                        _progress.emit(false)
                    }
                    WorkInfo.State.FAILED -> {
                        Log.d("TAG", "FAILED")
                        _progress.emit(false)
                        _error.emit(true)
                    }
                }

        }
            .onEach { repo.cleanWorkHistory() }
            .launchIn(viewModelScope)
    }

    fun downloadFile(uri: String) {
        repo.startDownload(uri)
    }

    fun stopDownload() {
        repo.stopDownload()
    }
}