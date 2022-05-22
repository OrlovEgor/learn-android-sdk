package ru.orlovegor.contentprovider.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun <T : Fragment> T.toastFragment(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

const val TAG_LOGGER = "TAG"
