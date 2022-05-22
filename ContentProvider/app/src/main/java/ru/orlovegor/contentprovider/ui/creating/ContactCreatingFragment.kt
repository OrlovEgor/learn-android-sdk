package ru.orlovegor.contentprovider.ui.creating

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.contentprovider.R
import ru.orlovegor.contentprovider.databinding.FragmentContactCreatingBinding
import ru.orlovegor.contentprovider.utils.emailPattern
import ru.orlovegor.contentprovider.utils.phonePattern
import ru.orlovegor.contentprovider.utils.toastFragment

class ContactCreatingFragment : Fragment(R.layout.fragment_contact_creating) {

    private val binding: FragmentContactCreatingBinding by viewBinding()
    private val viewModel: ContactCreatingViewModel by viewModels()

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            checkState()
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addWatcher(watcher)
        setClickButton()
        observeViewModelState()
    }

    private fun validationEmail(): String? {
        return if (binding.editTextAddContactEmail.text.isNotBlank() &&
            emailPattern.matcher(binding.editTextAddContactEmail.text).matches())
            binding.editTextAddContactEmail.text.toString()
         else if (binding.editTextAddContactEmail.text.isBlank())
            ""
        else null
    }

    private fun checkState() {
        binding.buttonAddContactAdd.isEnabled = binding.editTextAddContactName.text.isNotBlank()
                && binding.editTextAddContactLastName.text.isNotBlank()
                && phonePattern.matcher(binding.editTextAddContactPhoneNumber.text).matches()
    }

    private fun addWatcher(watcher: TextWatcher) =
        with(binding) {
            editTextAddContactLastName.addTextChangedListener(watcher)
            editTextAddContactName.addTextChangedListener(watcher)
            editTextAddContactPhoneNumber.addTextChangedListener(watcher)
            editTextAddContactEmail.addTextChangedListener(watcher)
        }


    private fun setClickButton() {
        binding.buttonAddContactAdd.setOnClickListener {
            viewModel.saveContact(
                binding.editTextAddContactName.text.toString(),
                binding.editTextAddContactLastName.text.toString(),
                binding.editTextAddContactPhoneNumber.text.toString(),
                validationEmail()
            )
        }
    }

    private fun observeViewModelState() {
        viewModel.toast.observe(viewLifecycleOwner,{toastResId -> toastFragment(toastResId)})
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner,{ findNavController().popBackStack() })

    }
}