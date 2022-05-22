package ru.orlovegor.notificationapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import ru.orlovegor.notificationapp.R
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTokenToTextView()
        setNavigateButton()
        NotificationManagerCompat.from(requireContext()).cancelAll()
        val button = view.findViewById<Button>(R.id.navigate_button)
        Log.d("TAG","Press button")
        button?.setOnClickListener {
            Log.d("TAG","Press button")
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToReceiveFragment())
        }
    }

    private fun setTokenToTextView() {
        val tokenTextView = view?.findViewById<TextView>(R.id.token_text_view)
        lifecycleScope.launch {
            val token = getToken()
            tokenTextView?.text = token
            Log.d("Tag", "$token")
        }
    }

    private fun setNavigateButton(){
        val button = view?.findViewById<Button>(R.id.navigate_button)
        Log.d("TAG","$button")
        button?.setOnClickListener{
            Log.d("TAG","$button")
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToReceiveFragment())
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