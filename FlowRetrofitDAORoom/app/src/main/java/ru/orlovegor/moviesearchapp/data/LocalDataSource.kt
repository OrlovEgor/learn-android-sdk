package ru.orlovegor.moviesearchapp.data

import ru.orlovegor.moviesearchapp.data.dao.Database
import ru.orlovegor.moviesearchapp.data.models.LocalMovie
import ru.orlovegor.moviesearchapp.data.models.MovieTypes

class LocalDataSource {
    private val movieDao = Database.instance.movieDao()

     fun insertMovies(movie: List<LocalMovie>) {
        movieDao.insert(movie)
    }

    fun getMovie(tittle: String, type: MovieTypes) = movieDao.getMovies(tittle, type)
}
