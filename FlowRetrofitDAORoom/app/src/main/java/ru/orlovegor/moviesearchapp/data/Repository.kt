package ru.orlovegor.moviesearchapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.orlovegor.moviesearchapp.data.dao.Database
import ru.orlovegor.moviesearchapp.data.models.*
import ru.orlovegor.moviesearchapp.utils.ResultWrapper
import java.io.IOException


class Repository() {

    private val remoteDataSource = RemoteDataSource()
    private val localDataSource = LocalDataSource()

     suspend fun saveMovie(movie: List<LocalMovie>) {
        withContext(Dispatchers.IO) {
            localDataSource.insertMovies(movie)
        }
    }
    suspend fun getLocalMovie(tittle: String, movieTypes: MovieTypes) =
        withContext(Dispatchers.IO){
            localDataSource.getMovie(tittle,movieTypes)
        }

    suspend fun testApiRequest(
        tittle: String,
        movieType: String
    ): ResultWrapper<List<RemoteMovie>> {
        return safeApiCall { remoteDataSource.loadMovie(tittle, movieType) }
    }

    private suspend fun <T> safeApiCall(call: suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(call.invoke())
        } catch (t: IOException) {
                ResultWrapper.NetworkError
        } catch (t: Throwable){
            ResultWrapper.Error(t)
        }
    }

}