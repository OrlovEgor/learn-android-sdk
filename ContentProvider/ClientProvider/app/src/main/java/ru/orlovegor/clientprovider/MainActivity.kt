package ru.orlovegor.clientprovider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.clientprovider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            setStateButtons()
        }
        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel by viewModels<ClientViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonClick()
        observeViewModelState()
        binding.idEditText.addTextChangedListener(textWatcher)
        binding.tittleEditText.addTextChangedListener(textWatcher)
    }


    private fun setButtonClick() {
        with(binding) {
            getAllCoursesButton.setOnClickListener { viewModel.getAllCourses() }
            getCoursesByIdButton.setOnClickListener {
                viewModel.getCourseById(idEditText.text.toString().toInt())
            }
            addCourseButton.setOnClickListener {
                viewModel.addCourse(
                    idEditText.text.toString().toInt(), tittleEditText.text.toString()
                )
            }
            deleteCourseById.setOnClickListener {
                viewModel.deleteCourseById(idEditText.text.toString().toInt())
            }
            deleteAllCourses.setOnClickListener { viewModel.deleteAllCourse() }
            updateCourseById.setOnClickListener {
                viewModel.updateCourseById(
                    idEditText.text.toString().toInt(),
                    tittleEditText.text.toString()
                )
            }
        }
    }

    private fun observeViewModelState() {
        viewModel.courseLiveData.observe(
            this, { binding.coursesTextView.text = it.toString() })
        viewModel.showToastLiveData.observe(this, { showToast(it) })
    }

    private fun showToast(@StringRes resID: Int) {
        Toast.makeText(this, resID, Toast.LENGTH_SHORT).show()
    }

    private fun setStateButtons() {
        if (checkEmptyOrNumber(binding.idEditText.text.toString()) &&
            binding.tittleEditText.text.isNotBlank()
        ) {
            binding.addCourseButton.isEnabled = true
            binding.updateCourseById.isEnabled = true
        } else if ((checkEmptyOrNumber(binding.idEditText.text.toString()))) {
            binding.deleteCourseById.isEnabled = true
            binding.getCoursesByIdButton.isEnabled = true
        } else {
            binding.addCourseButton.isEnabled = false
            binding.updateCourseById.isEnabled = false
            binding.deleteCourseById.isEnabled = false
            binding.getCoursesByIdButton.isEnabled = false
        }
    }
}


