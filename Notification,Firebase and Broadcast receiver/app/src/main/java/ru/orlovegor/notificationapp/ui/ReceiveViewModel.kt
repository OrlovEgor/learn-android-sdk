package ru.orlovegor.notificationapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import ru.orlovegor.notificationapp.data.Repository

class ReceiveViewModel(application: Application):AndroidViewModel(application) {

   // init {
    //    Log.d("CON", "View model running")
   // }
    private val repo = Repository(application)

   // private val _isProgress = MutableLiveData<Boolean>(repo.status.observe(viewModelScope, {}))
    private val _isOnline = MutableLiveData<Boolean>(false)
    private val _showToast = MutableLiveData<Int>()
    val isProgress = liveData<Boolean > { emitSource(repo.fofofo().asLiveData())
    Log.d("CON", "VM status update ")}

   // val isProgress: LiveData<Boolean>
    //get() = _isProgress

    val isOnline: LiveData<Boolean>
    get() = _isOnline

    val showToast: LiveData<Int>
    get() = _showToast

    fun startProgress(){

    }

    fun changeOnlineStatus(status: Boolean){
        _isOnline.postValue(status)
    }

}