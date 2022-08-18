package ru.orlovegor.material.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.orlovegor.material.MovieAdapter
import ru.orlovegor.material.R
import ru.orlovegor.material.databinding.FragmentFirstBinding
import ru.orlovegor.material.presentation.FirstViewModel
import ru.orlovegor.material.utils.ItemOffsetDecoration

import ru.orlovegor.material.utils.autoCleared

class FirstFragment : Fragment(R.layout.fragment_first) {

    private val binding: FragmentFirstBinding by viewBinding()
    private val viewModel: FirstViewModel by viewModels()
    private var movieListAdapter: MovieAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observableViewModelState()
        lifecycleScope.launch {
            viewModel.getMovies()
            delay(4000)
            showSnack()
        }
    }

    private fun observableViewModelState(){
        lifecycleScope.launchWhenStarted {
            viewModel.movies.collect{
                movieListAdapter.submitList(it)
            }
        }
    }

    private fun showSnack(){
        Snackbar.make(requireView(),R.string.snack_no_connection, Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) {
                Snackbar.make(requireView(),R.string.list_updated, Snackbar.LENGTH_SHORT)
                    .show()
            }
            .show()
    }

    private fun initList() {
        movieListAdapter = MovieAdapter()
        with(binding.rwList){
            adapter = movieListAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            addItemDecoration(ItemOffsetDecoration(44))
        }
    }
}