package ru.orlovegor.videostorage.utils

import android.os.Build
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


fun haveQ() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

fun <T : Fragment> T.toastFragmentResId(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun getMIMEType(url: String) =
    MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url))


