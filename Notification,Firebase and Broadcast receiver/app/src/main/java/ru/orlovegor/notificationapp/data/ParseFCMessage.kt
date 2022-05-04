package ru.orlovegor.notificationapp.data

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

/*interface ParseFCMessage {
    fun parseDate(message: String): ChatMessage?

    class Base : ParseFCMessage {
        override fun parseDate(message: String): ChatMessage? {
            val moshi = Moshi.Builder().build()
            val chatMessageAdapter: JsonAdapter<ChatMessage> =
                moshi.adapter(ChatMessage::class.java)
            return chatMessageAdapter.fromJson(message)
        }

    }
}*/
