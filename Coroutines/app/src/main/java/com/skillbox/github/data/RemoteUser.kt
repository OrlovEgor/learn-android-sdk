package com.skillbox.github.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteUser(

    @Json(name = "login")
    override val name: String,

    @Json(name = "avatar_url")
    val avatarLink: String
) : AbstractUser()
