package ru.orlovegor.videostorage.data

import android.net.Uri

data class Video(
    val id: Long,
    val name: String,
    val size: Int,
    val uri: Uri
)
