package ru.orlovegor.moviesearchapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import ru.orlovegor.moviesearchapp.data.MovieTypes

class MovieViewModel: ViewModel() {

    fun bind (queryFlow: Flow<String>, movieTypeFlow: Flow<MovieTypes>){

    }
}