package ru.orlovegor.moviesearchapp.networking

import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovegor.moviesearchapp.data.RemoteMovie

interface MovieApi {
    @GET("/")
   suspend fun getMovieByTittle(
        @Query("s") tittle: String
    ): List<RemoteMovie>
}