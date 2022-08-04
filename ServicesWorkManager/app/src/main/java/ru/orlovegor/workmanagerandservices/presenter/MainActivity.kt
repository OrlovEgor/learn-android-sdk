package ru.orlovegor.workmanagerandservices.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.workmanagerandservices.R
import ru.orlovegor.workmanagerandservices.databinding.ActivityMainBinding
import ru.orlovegor.workmanagerandservices.utils.makeToast

class MainActivity : AppCompatActivity() {

    private val myTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.startButton.isEnabled = binding.urlEditText.text.isNotBlank()
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.urlEditText.addTextChangedListener(myTextWatcher)
        bindButtons()
        observeViewModelState()
    }

    private fun observeViewModelState() {
        with(lifecycleScope) {
            launchWhenStarted {
                viewModel.progress.collect { visibility ->
                    binding.progressBar.isVisible = visibility
                    binding.stopDownloadingButton.isVisible = visibility
                }
            }
            launchWhenStarted {
                viewModel.error.collect { visibility ->
                    binding.errorGroup.isVisible = visibility
                }
            }
            launchWhenStarted {
                viewModel.toast.collect { message ->
                    makeToast(message, applicationContext)
                }
            }
        }
    }

    private fun bindButtons() {
        with(binding) {

            startButton.setOnClickListener {
                viewModel.downloadFile(binding.urlEditText.text.toString())
            }

            errorButtonRetry.setOnClickListener {
                viewModel.downloadFile(binding.urlEditText.text.toString())
            }
            stopDownloadingButton.setOnClickListener {
                viewModel.stopDownload()
            }
        }
    }
}