package ru.orlovegor.contentprovider.ui.detailInfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.contentprovider.R
import ru.orlovegor.contentprovider.databinding.FragmentContactDetailInfoBinding
import ru.orlovegor.contentprovider.utils.toastFragment

class ContactDetailInfoFragment : Fragment(R.layout.fragment_contact_detail_info) {
    private val binding: FragmentContactDetailInfoBinding by viewBinding()
    private val args: ContactDetailInfoFragmentArgs by navArgs()
    private val viewModel by viewModels<ContactDetailInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContactArgs()
        setClickDeleteContactButton()
        observerViewModelState()
    }

    private fun setContactArgs() {
        binding.textViewDetailContactName.text = args.contact.name
        binding.textViewDetailPhoneNumber.text = args.contact.phones.joinToString("\n")
        binding.textViewDetailEmail.text = args.contact.email.joinToString("\n")
    }

    private fun setClickDeleteContactButton() {
        binding.buttonDetailDeleteContact.setOnClickListener {
            viewModel.deleteContact(args.contact.id)
        }
    }

    private fun observerViewModelState() {
        viewModel.showToastLiveData.observe(
            viewLifecycleOwner,
             { toastID -> toastFragment(toastID) })
        viewModel.successLiveData.observe(
            viewLifecycleOwner,
             { findNavController().popBackStack() }
        )
    }
}