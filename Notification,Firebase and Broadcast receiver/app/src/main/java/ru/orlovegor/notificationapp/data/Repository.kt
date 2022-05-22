package ru.orlovegor.notificationapp.data

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import ru.orlovegor.notificationapp.ui.ConnectionStatus

class Repository(
    private val context: Context
): ConnectionStatus {

    fun parseNotification(type: String, data: String): NotificationMessages? {
        val moshi = Moshi.Builder().build()
        val chatMessageAdapter: JsonAdapter<NotificationMessages.ChatMessage> =
            moshi.adapter(NotificationMessages.ChatMessage::class.java)
        val newPromotionsAdapter: JsonAdapter<NotificationMessages.NewPromotions> =
            moshi.adapter(NotificationMessages.NewPromotions::class.java)
        return try {
            when (type) {
                NotificationType.MESSAGE.type ->
                    chatMessageAdapter.fromJson(data) as NotificationMessages
                NotificationType.PROMOTION.type ->
                    newPromotionsAdapter.fromJson(data) as NotificationMessages
                else -> null
            }
        } catch (t: Throwable) {
            return null
        }
    }

    var status = false

    fun fofofo() = flow<Boolean> { emit(status) }

    override fun getStatus(status:Boolean) {
        fofofo()
        Log.d("CON", "start fun get status in repo status = $status")
    }

}

