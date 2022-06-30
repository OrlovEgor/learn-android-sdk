package ru.orlovegor.moviesearchapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.RadioGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.data.MovieTypes

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                trySendBlocking(s?.toString().orEmpty()
                ).onFailure { Log.d("TAG", "error textChangedFlow") }

            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        this@textChangedFlow.addTextChangedListener(textChangedListener)

        awaitClose {
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}

fun RadioGroup.checkedChangesFlow(): Flow<Int> {
    return callbackFlow {
        val checkedChangeListener = RadioGroup.OnCheckedChangeListener { _, resIdRadioButton ->
            trySendBlocking(resIdRadioButton)
        }
        setOnCheckedChangeListener(checkedChangeListener)
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }
}


/*private fun flowFromGroupChanged(): Flow<MovieTypes> {
    viewLifecycleOwner.lifecycleScope.launch {
        Log.d("TAG", "fragment flowGroupChanged start")
        callbackFlow {
            binding.movieTypeRadioGroup.checkedChangesFlow()
                .map {
                    when (it) {
                        R.id.radio_button_movie -> MovieTypes.MOVIE
                        R.id.radio_button_episode -> MovieTypes.EPISODE
                        R.id.radio_button_series -> MovieTypes.SERIES
                        else -> {}
                    }
                }
        }
    }
}*/
