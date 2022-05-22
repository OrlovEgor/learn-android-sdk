package ru.orlovegor.daoroom.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun <T : Fragment> T.toastFragmentResId(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun <T : Fragment> T.toastFragmentString(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}