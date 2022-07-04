package ru.orlovegor.moviesearchapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.adapters.MovieAdapter
import ru.orlovegor.moviesearchapp.data.MovieTypes
import ru.orlovegor.moviesearchapp.data.Repository
import ru.orlovegor.moviesearchapp.databinding.FragmentMovieSearchBinding
import ru.orlovegor.moviesearchapp.utils.autoCleared
import ru.orlovegor.moviesearchapp.utils.checkedChangesFlow
import ru.orlovegor.moviesearchapp.utils.textChangedFlow



class MovieSearchFragment : Fragment(R.layout.fragment_movie_search) {

    private val binding: FragmentMovieSearchBinding by viewBinding()

    private var movieListAdapter: MovieAdapter by autoCleared()

    private val viewModel: MovieViewModel by viewModels()




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        viewLifecycleOwner.lifecycleScope.launch {
            sendDataToViewModel()
        }
        observeViewModelState()
    }


    private fun sendDataToViewModel(){
        viewModel.bind(
            binding.searchEditText.textChangedFlow(),
            binding.movieTypeRadioGroup.checkedChangesFlow()
                .map {
                    when (it) {
                        R.id.radio_button_movie -> MovieTypes.MOVIE
                        R.id.radio_button_episode -> MovieTypes.EPISODE
                        R.id.radio_button_series -> MovieTypes.SERIES
                        else -> {MovieTypes.MOVIE}
                    }
                }
        )
    }

    private fun observeViewModelState(){
        viewModel.listMovie.observe(viewLifecycleOwner){movieListAdapter.submitList(it)}
    }

    private fun initList() {
        movieListAdapter = MovieAdapter()
        with(binding.movieListRw) {
            adapter = movieListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        viewModel.cancelJob()
        super.onDestroy()
    }
}