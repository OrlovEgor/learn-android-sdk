package ru.orlovegor.moviesearchapp.data

import kotlinx.coroutines.flow.Flow
import ru.orlovegor.moviesearchapp.data.dao.Database
import ru.orlovegor.moviesearchapp.data.models.LocalMovie
import ru.orlovegor.moviesearchapp.data.models.MovieTypes

class LocalDataSource {
    private val movieDao = Database.instance.movieDao()

     fun insertMovies(movie: List<LocalMovie>) {
        movieDao.insert(movie)
    }

    fun getAllMovie(): Flow<List<LocalMovie>> = movieDao.getAllMovies()

    fun getMovie(tittle: String, type: MovieTypes) = movieDao.getMovies(tittle, type)
}
