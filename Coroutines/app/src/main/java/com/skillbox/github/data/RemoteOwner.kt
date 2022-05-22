package com.skillbox.github.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteOwner(

    @Json(name = "avatar_url")
    val avatarLink: String,

    @Json(name = "login")
    val ownerName: String
)
