package com.skillbox.homework19.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Animal : Parcelable {
    @Parcelize
    data class Pet(
        val name: String,
        val age: Int,
        val photoLink: String,
        val id: Long
    ) : Animal()

    @Parcelize
    data class Wild(
        val name: String,
        val age: Int,
        val photoLink: String,
        val aggression: String,
        val id: Long
    ) : Animal()
}