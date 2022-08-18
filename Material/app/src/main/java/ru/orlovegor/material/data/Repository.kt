package ru.orlovegor.material.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.orlovegor.material.networking.Network

class Repository {

   suspend fun fetchMovie(tittle: String) = withContext(Dispatchers.IO){
       Network.movieApi.getMovieByTittle(tittle)
   }
}