package ru.orlovegor.material.networking

import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovegor.material.data.RemoteMovie

interface MovieApi {
    @GET("/")
   suspend fun getMovieByTittle(
        @Query("s") tittle: String
    ): List<RemoteMovie>
}