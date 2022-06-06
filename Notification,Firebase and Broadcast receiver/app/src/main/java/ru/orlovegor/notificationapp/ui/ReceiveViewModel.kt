package ru.orlovegor.notificationapp.ui

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.orlovegor.notificationapp.R
import ru.orlovegor.notificationapp.data.NetworkBroadcastReceiver
import ru.orlovegor.notificationapp.utils.SingleLiveEvent

class ReceiveViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application

    private val networkBroadcastReceiver = NetworkBroadcastReceiver(this)

    private val isConnection = MutableLiveData(false)
    private val _toast = SingleLiveEvent<Int>()
    private val _showNotification = SingleLiveEvent<Unit>()

    val showNotification: LiveData<Unit>
        get() = _showNotification
    val toast: LiveData<Int>
        get() = _toast

    init {
        context.registerReceiver(
            networkBroadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    fun checkStatus() {
        if (isConnection.value == true)
            _showNotification.postValue(Unit)
        else
            _toast.postValue(R.string.check_internet)
    }

    fun setConnectionStatus(status: Boolean) {
        isConnection.postValue(status)
        Log.d("ReceiveViewModel", "Status changed: status = $status")
    }

    override fun onCleared() {
        super.onCleared()
        context.unregisterReceiver(networkBroadcastReceiver)
    }

}