package ru.orlovegor.moviesearchapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.orlovegor.moviesearchapp.networking.Network

class Repository {

    suspend fun getMovie(tittle: String, type: String): List<RemoteMovie> =
         Network.movieApi.getMovieByTittle(tittle, type)

}