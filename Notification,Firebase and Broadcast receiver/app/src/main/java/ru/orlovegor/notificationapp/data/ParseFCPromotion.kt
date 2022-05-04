package ru.orlovegor.notificationapp.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

/*
interface ParseFCPromotion {
    fun parseDate(message: String): NewPromotions?

    class Base : ParseFCPromotion {
        override fun parseDate(message: String): NewPromotions? {
            val moshi = Moshi.Builder().build()
            val newPromotionsAdapter: JsonAdapter<NewPromotions> =
                moshi.adapter(NewPromotions::class.java)
            return newPromotionsAdapter.fromJson(message)
        }
    }
}*/
