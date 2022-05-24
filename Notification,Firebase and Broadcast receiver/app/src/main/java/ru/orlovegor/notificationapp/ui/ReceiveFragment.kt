package ru.orlovegor.notificationapp.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.orlovegor.notificationapp.R
import ru.orlovegor.notificationapp.data.NetworkBroadcastReceiver


class ReceiveFragment : Fragment(R.layout.fragment_receive) {

    private lateinit var networkReceiver: NetworkBroadcastReceiver
    private val connectionStatus: Boolean
        get() = networkReceiver.status

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkReceiver = NetworkBroadcastReceiver()
        val button = view.findViewById<Button>(R.id.start_button)
        button.setOnClickListener {
            Log.d("STATUS", "status = $connectionStatus")
            if (!connectionStatus) {
                Toast.makeText(requireContext(), R.string.check_internet, Toast.LENGTH_LONG).show()
            } else {
                showProgressNotification()
            }
        }
    }

    private fun showProgressNotification() {
        val notificationBuilder = NotificationCompat.Builder(
            requireContext(),
            NotificationChannels.CHAT_MESSAGE_ID
        )
            .setContentTitle("Update downloading")
            .setContentText("Download in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_emotions_24)
        val maxProgress = 10
        hideButton(view?.findViewById(R.id.start_button), false)
        lifecycleScope.launch {
            (0 until maxProgress).forEach { progress ->
                val notification = notificationBuilder
                    .setProgress(maxProgress, progress, false)
                    .build()

                NotificationManagerCompat.from(requireContext())
                    .notify(PROGRESS_NOTIFICATION_ID, notification)

                delay(500)
            }

            val finalNotification = notificationBuilder
                .setContentText("Download is completed")
                .setProgress(0, 0, false)
                .build()

            NotificationManagerCompat.from(requireContext())
                .notify(PROGRESS_NOTIFICATION_ID, finalNotification)
            delay(500)

            NotificationManagerCompat.from(requireContext())
                .cancel(PROGRESS_NOTIFICATION_ID)
            hideButton(view?.findViewById(R.id.start_button), true)
        }
    }

    private fun hideButton(button: Button?, isActive: Boolean) {
        button?.isEnabled = isActive
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(networkReceiver)
    }

    companion object {
        const val PROGRESS_NOTIFICATION_ID = 232
    }

}