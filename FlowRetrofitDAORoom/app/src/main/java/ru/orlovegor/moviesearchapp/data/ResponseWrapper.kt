package ru.orlovegor.moviesearchapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class  ResponseWrapper<T>  {
    @Json(name = "Response")
    val data: T? = null
    @Json(name = "Error")
    val error: Error? = null
}
