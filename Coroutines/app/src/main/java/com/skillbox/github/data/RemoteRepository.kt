package com.skillbox.github.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRepository(

    @Json(name = "name")
    val name: String,

    @Json(name = "owner")
    val userData: RemoteOwner,

    @Json(name = "id")
    val id: String

)
