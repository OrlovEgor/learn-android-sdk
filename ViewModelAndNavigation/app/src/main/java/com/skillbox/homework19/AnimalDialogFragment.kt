package com.skillbox.homework19

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.homework19.databinding.FragmentDialogAnimalBinding
import kotlin.random.Random

class AnimalDialogFragment : DialogFragment() {

    private val binding: FragmentDialogAnimalBinding by viewBinding()
    private val animalViewModel: AnimalViewModel by navGraphViewModels(R.id.nav_graph)

    private val myTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkDataAndTurnOnButtonApply()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_animal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameEditText.addTextChangedListener(myTextWatcher)
        binding.ageEditText.addTextChangedListener(myTextWatcher)
        binding.isWildCheckBox.setOnClickListener {
            showAggressiveCheckbox()
        }
        binding.cancelButton.setOnClickListener {
            dialog?.cancel()
        }
        binding.applyButton.setOnClickListener {
            addNewAnimal()
            dismiss()
        }
    }

    private fun showAggressiveCheckbox() {
        binding.isAggressiveCheckBox.isEnabled = binding.isWildCheckBox.isChecked
    }

    private fun addNewAnimal() {
        animalViewModel.addAnimal(
            isWild = binding.isWildCheckBox.isChecked,
            name = binding.nameEditText.text.toString(),
            age = Integer.parseInt(binding.ageEditText.text.toString()),
            isAggressive = binding.isAggressiveCheckBox.isChecked
        )
    }

    private fun checkDataAndTurnOnButtonApply() {
        binding.applyButton.isEnabled = binding.nameEditText.text.isNotBlank()
                && binding.ageEditText.text.isNotBlank()
    }
}

