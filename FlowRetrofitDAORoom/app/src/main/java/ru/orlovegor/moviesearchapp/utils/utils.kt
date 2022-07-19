package ru.orlovegor.moviesearchapp.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun toastFragment(@StringRes message: Int, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
