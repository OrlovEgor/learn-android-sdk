package ru.orlovegor.moviesearchapp.data

import android.util.Log
import com.squareup.moshi.JsonDataException

import ru.orlovegor.moviesearchapp.networking.Network

class Repository {

    suspend fun getMovie(tittle: String, type: String): List<RemoteMovie> = try {
        Network.movieApi.getMovieByTittle(tittle, type)
    } catch (j: JsonDataException) {
        Log.d("TAG", " Json data exception = $j")
        listOf<RemoteMovie>()
    }
}