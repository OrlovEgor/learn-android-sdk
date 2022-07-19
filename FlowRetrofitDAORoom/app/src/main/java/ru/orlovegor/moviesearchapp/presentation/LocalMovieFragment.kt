package ru.orlovegor.moviesearchapp.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.adapters.MovieAdapter
import ru.orlovegor.moviesearchapp.databinding.FragmentLocalMovieBinding
import ru.orlovegor.moviesearchapp.utils.autoCleared

class LocalMovieFragment : Fragment(R.layout.fragment_local_movie) {

    private val binding: FragmentLocalMovieBinding by viewBinding()

    private var movieListAdapter: MovieAdapter by autoCleared()

    private val viewModel: LocalMovieViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModelSate()
    }

    private fun initList() {
        movieListAdapter = MovieAdapter()
        with(binding.localMovieListRw) {
            adapter = movieListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeViewModelSate() {
        lifecycleScope.launchWhenStarted {
            viewModel.listMovie.collect {
                movieListAdapter.submitList(it)
            }
        }
    }
}