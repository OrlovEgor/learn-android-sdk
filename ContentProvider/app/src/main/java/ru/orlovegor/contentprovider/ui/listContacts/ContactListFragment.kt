package ru.orlovegor.contentprovider.ui.listContacts

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import ru.orlovegor.contentprovider.R
import ru.orlovegor.contentprovider.adapters.ContactsAdapter
import ru.orlovegor.contentprovider.databinding.FragmentContactListBinding
import ru.orlovegor.contentprovider.utils.autoCleared
import ru.orlovegor.contentprovider.utils.toastFragment


class ContactListFragment : Fragment(R.layout.fragment_contact_list) {
    private val binding: FragmentContactListBinding by viewBinding()
    private var contactAdapter: ContactsAdapter by autoCleared()
    private val viewModel by viewModels<ContactListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observerViewModelState()

        Handler(Looper.getMainLooper()).post { // запрашиваем разрешения для работы с контактми
            constructPermissionsRequest( // это подключаемая бибилотека
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS, // запрашиваем разрешение
                onShowRationale = ::onContactPermissionShowRationale,// показ рашионале
                onPermissionDenied = ::onContactPermissionDenied, // запретил
                onNeverAskAgain = ::onContactPermissionNeverAskAgain, // не принял
                requiresPermission = { viewModel.loadList() } // если пользователь подтвердил то  ок  запускаем
            )
                .launch()
        }
        bindFABButton()
        bindDownloadFragmentButton()
    }

    private fun initRecyclerView() {
        contactAdapter = ContactsAdapter { contact ->
            findNavController()
                .navigate(
                    ContactListFragmentDirections.actionContactListFragmentToContactDetailInfoFragment(
                        contact
                    )
                )
        }
        with(binding.listContactList) {
            adapter = contactAdapter
            setHasFixedSize(true)
        }
    }

    private fun bindDownloadFragmentButton() {
        binding.downloadButtonListContact.setOnClickListener {
            findNavController().navigate(ContactListFragmentDirections.actionContactListFragmentToDownloadFragment())
        }
    }

    private fun bindFABButton() {
        binding.listContactFabButton.setOnClickListener {
            findNavController().navigate(ContactListFragmentDirections.actionContactListFragmentToContactCreatingFragment())
        }
    }

    private fun observerViewModelState() =
        viewModel.contactsLiveData.observe(viewLifecycleOwner) { contactAdapter.submitList(it) }

    private fun onContactPermissionDenied() = toastFragment(R.string.contact_permissions_denied)

    private fun onContactPermissionShowRationale(request: PermissionRequest) = request.proceed()

    private fun onContactPermissionNeverAskAgain() =
        toastFragment(R.string.contact_permissions_never_ask_again)

}