package ru.orlovegor.moviesearchapp.data

import ru.orlovegor.moviesearchapp.networking.Network

class RemoteDataSource {
    suspend fun loadMovie(tittle: String, type: String) =
        Network.movieApi.getMovieByTittle(tittle, type)

}