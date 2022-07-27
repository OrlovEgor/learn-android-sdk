package ru.orlovegor.workmanagerandservices.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun makeToast(@StringRes text: Int, context: Context) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}