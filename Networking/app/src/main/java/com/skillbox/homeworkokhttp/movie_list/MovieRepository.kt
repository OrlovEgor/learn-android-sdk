package com.skillbox.homeworkokhttp.movie_list


import android.text.TextUtils
import com.skillbox.homeworkokhttp.network.Network
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MovieRepository {

    fun searchMovie(
        tittle: String,
        year: String,
        genre: String,
        callback: (ArrayList<RemoteMovie>) -> Unit,
        errorCallback: (e: Throwable) -> Unit
    ): Call {
        return Network.getSearchMovieCall(tittle, year, genre).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    errorCallback(IOException("connection error"))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        val movies = parseMovieResponse(responseString)
                        callback(movies)
                    } else
                        errorCallback(IOException("Server error"))
                }
            })
        }
    }

    private fun parseMovieResponse(responseBodyString: String): ArrayList<RemoteMovie> {
        try {
            val jsonObject = JSONObject(responseBodyString)
            val movieArray = jsonObject.getJSONArray("Search")
            val movies =
                (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                    .map { movieJsonObject ->
                        val tittle = movieJsonObject.getString("Title")
                        val year = movieJsonObject.getString("Year")
                        val id = movieJsonObject.getString("imdbID")
                        val genre = movieJsonObject.getString("Type")
                        val poster = movieJsonObject.getString("Poster")
                        RemoteMovie(
                            tittle = tittle,
                            year = year,
                            id = id,
                            genre = genre,
                            poster = poster
                        )
                    }
            return movies as ArrayList<RemoteMovie>

        } catch (e: JSONException) {
            return arrayListOf()
        }
    }

    fun checkYear(year: String): Boolean {
        val isNumber = TextUtils.isDigitsOnly(year)
        return if (year.isNotBlank()&& isNumber ){
            val yearToInt =   Integer.parseInt(year)
            yearToInt <= 2021 && isNumber
        } else true

    }
}