package com.skillbox.homeworkokhttp.movie_list

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.homeworkokhttp.R
import com.skillbox.homeworkokhttp.databinding.FragmentMovieBinding
import com.skillbox.homeworkokhttp.utils.autoCleared

class MovieListFragment() : Fragment(R.layout.fragment_movie) {

    private val binding: FragmentMovieBinding by viewBinding()
    private var movieListAdapter: MovieListAdapter by autoCleared<MovieListAdapter>()
    private val viewModel: MovieListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initAutoCompleteTextView()
        observeViewModelState()
        bindSearchButton()
        bindRetryRequestButton()
    }

    private fun initList() {
        movieListAdapter = MovieListAdapter()
        with(binding.listMovie) {
            adapter = movieListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun initAutoCompleteTextView() {
        val text = resources.getStringArray(R.array.genre_string_array)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, text)
        binding.typeTextView.setAdapter(adapter)

    }

    private fun observeViewModelState() {
        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieList.observe(viewLifecycleOwner) {
            movieListAdapter.submitList(it)
            viewModel.isErrorLoading.observe(viewLifecycleOwner, ::showErrorViewGroup)
        }
        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                getString(R.string.enter_correct_year),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun giveDataToSearchMovie() {
        val queryTitle = binding.tittleMovieEditText.text.toString()
        val queryYear = binding.yearReleaseEditText.text.toString()
        val queryGenre = if (binding.typeTextView.text.toString() == "not chosen") {
            ""
        } else {
            binding.typeTextView.text.toString()
        }
        binding.typeTextView.text.toString()
        viewModel.search(queryTitle, queryYear, queryGenre)
    }

    private fun bindSearchButton() {
        binding.searchMovieButton.setOnClickListener {
            giveDataToSearchMovie()
        }
    }

    private fun bindRetryRequestButton() {
        binding.retryButton.setOnClickListener {
            giveDataToSearchMovie()
        }
    }

    private fun showErrorViewGroup(isError: Boolean) {
        binding.messageErrorGroup.isVisible = isError
    }

    private fun updateLoadingState(isLoading: Boolean) {
        with(binding) {
            listMovie.isEnabled = !isLoading
            searchMovieButton.isEnabled = !isLoading
            tittleMovieEditText.isEnabled = !isLoading
            yearReleaseEditText.isEnabled = !isLoading
            typeTextView.isEnabled = !isLoading
            movieProgressBar.isVisible = isLoading
        }
    }
}