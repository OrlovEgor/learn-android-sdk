package com.skillbox.github.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Follows(

    @Json(name = "login")
    override val name: String

) : AbstractUser()
