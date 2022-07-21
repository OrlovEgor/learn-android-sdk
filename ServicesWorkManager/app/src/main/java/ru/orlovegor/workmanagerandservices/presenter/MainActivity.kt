package ru.orlovegor.workmanagerandservices.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.workmanagerandservices.R
import ru.orlovegor.workmanagerandservices.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}