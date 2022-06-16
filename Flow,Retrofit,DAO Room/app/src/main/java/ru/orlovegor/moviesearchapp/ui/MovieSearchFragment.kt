package ru.orlovegor.moviesearchapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.data.Repository



class MovieSearchFragment: Fragment(R.layout.fragment_movie_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.search_edit_text)
        button.setOnClickListener {
            lifecycleScope.launch {
                val movies = Repository().getMovie("Star")
                Log.d("TAG", " movie = $movies")
            }
        }
    }
}