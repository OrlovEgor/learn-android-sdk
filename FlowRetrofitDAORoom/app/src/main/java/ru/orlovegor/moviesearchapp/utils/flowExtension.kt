package ru.orlovegor.moviesearchapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioGroup
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                trySendBlocking(s?.toString().orEmpty())
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
            setOnCheckedChangeListener (null)
        }
    }
}
