package ru.orlovegor.moviesearchapp.data.models

import androidx.room.TypeConverter

class MovieTypesConverter {

    @TypeConverter
    fun converterMovieTypeToString(movieType: MovieTypes) = movieType.name

    @TypeConverter
    fun converterStringToMovieType(movieType: String) = MovieTypes.valueOf(movieType)

}