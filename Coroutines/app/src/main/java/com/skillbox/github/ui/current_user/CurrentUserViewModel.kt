package com.skillbox.github.ui.current_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.data.Follows
import com.skillbox.github.data.RemoteUser
import com.skillbox.github.repositories.CurrentUserRepo
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.*


class CurrentUserViewModel : ViewModel() {

    private val currentUserRepository = CurrentUserRepo()

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        showToastLiveData.postValue(Unit)
        isLoadingLiveData.postValue(false)
        Log.d("response", "Error ${throwable.message}")
    }

    private val scope = CoroutineScope(errorHandler + Job(SupervisorJob()) + Dispatchers.Main)

    private val currentUserLiveData = MutableLiveData<RemoteUser>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val showToastLiveData = SingleLiveEvent<Unit>()
    private val followsLiveData = MutableLiveData<List<Follows>>()

    val follows: LiveData<List<Follows>>
        get() = followsLiveData
    val currentUser: LiveData<RemoteUser>
        get() = currentUserLiveData
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData
    val showToast: LiveData<Unit>
        get() = showToastLiveData

    fun fetchUser() {
        scope.launch {
            isLoadingLiveData.postValue(true)
            val deferredResult = async {
                currentUserRepository.getUserData()
            }
            val deferredResult2 = async {
                currentUserRepository.getFollows()
            }
            currentUserLiveData.postValue(deferredResult.await())
            followsLiveData.postValue(deferredResult2.await())
            if (deferredResult.isCompleted && deferredResult2.isCompleted) {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}