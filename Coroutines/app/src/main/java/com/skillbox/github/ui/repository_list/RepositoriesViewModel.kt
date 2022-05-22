package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.data.RemoteRepository
import com.skillbox.github.repositories.RepositoriesRepo
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.*

class RepositoriesViewModel : ViewModel() {

    private val repository = RepositoriesRepo()

    private val repositoryListLiveData = MutableLiveData<ArrayList<RemoteRepository>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val showToastLiveData = SingleLiveEvent<Unit>()

    val repositoryList: LiveData<ArrayList<RemoteRepository>>
        get() = repositoryListLiveData
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData
    val showToast: LiveData<Unit>
        get() = showToastLiveData

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable -> }
    private val scope = CoroutineScope(Job(SupervisorJob()) + Dispatchers.Main + errorHandler)

    fun search() {
        scope.launch {
            yield()
            isLoadingLiveData.postValue(true)
            try {
                val repositories = repository.getRepositories()
                repositoryListLiveData.postValue(repositories as ArrayList<RemoteRepository>)
            } catch (t: Throwable) {
                showToastLiveData.postValue(Unit)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }
}
