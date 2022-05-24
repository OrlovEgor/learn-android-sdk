package ru.orlovegor.notificationapp.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import ru.orlovegor.notificationapp.ui.ReceiveFragment

class NetworkBroadcastReceiver : BroadcastReceiver() {

    var status = false

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        status = checkNetworkConnection(context)
        Log.d("CON", "On receive")
    }

    private fun checkNetworkConnection(context: Context): Boolean {
        val connectManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectManager.activeNetwork ?: return false
            val activeNetwork = connectManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}