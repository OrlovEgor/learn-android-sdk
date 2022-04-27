package ru.orlovegor.notificationapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tokenTextView = view.findViewById<TextView>(R.id.token_text_view)
        setTokenToTextView()
    }

    private fun setTokenToTextView() {
        val tokenTextView = view?.findViewById<TextView>(R.id.token_text_view)
        lifecycleScope.launch {
            tokenTextView?.text = getToken()
        }
    }

    private suspend fun getToken(): String? = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                continuation.resume(token)
            }
            .addOnFailureListener { exception ->
                continuation.resume(null)
            }
            .addOnCanceledListener {
                continuation.resume(null)
            }
    }
}