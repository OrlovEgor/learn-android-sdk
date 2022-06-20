package ru.orlovegor.moviesearchapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.adapters.MovieAdapter
import ru.orlovegor.moviesearchapp.data.Repository
import ru.orlovegor.moviesearchapp.databinding.FragmentMovieSearchBinding
import ru.orlovegor.moviesearchapp.utils.autoCleared


class MovieSearchFragment : Fragment(R.layout.fragment_movie_search) {

    private val binding: FragmentMovieSearchBinding by viewBinding()

    private var movieListAdapter: MovieAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         initList()
        binding.searchEditText.setOnClickListener {
            lifecycleScope.launch {
                val movies = Repository().getMovie("Star")
                Log.d("TAG", " movie = $movies")
                 movieListAdapter.submitList(movies)
            }

        }
    }

    private fun initList() {
        val list = view?.findViewById<RecyclerView>(R.id.movie_list_rw)
        movieListAdapter = MovieAdapter()
        with(binding.movieListRw) {
            this?.adapter = movieListAdapter
            this?.layoutManager = LinearLayoutManager(requireContext())
            this?.setHasFixedSize(true)
        }

    }
}