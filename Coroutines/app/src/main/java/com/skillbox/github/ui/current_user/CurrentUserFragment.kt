package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.data.Follows
import com.skillbox.github.data.RemoteUser
import com.skillbox.github.databinding.FragmentCurrentUserBinding
import com.skillbox.github.utils.toast

class CurrentUserFragment : Fragment(R.layout.fragment_current_user) {

    private val binding: FragmentCurrentUserBinding by viewBinding()
    private val viewModel: CurrentUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUser()
    }

    private fun setDataView(user: RemoteUser) {
        binding.userNameTextView.text = user.name

        Glide.with(this)
            .load(user.avatarLink)
            .into(binding.userAvatarImageView)
    }
    private fun setUserDataFollowsView(follows: List<Follows>){
        binding.followsTextView.text =  follows.joinToString { it.name }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        binding.currentUserProgressBar.isVisible = isLoading
    }

    private fun bindViewModel() {
        viewModel.follows.observe(viewLifecycleOwner, {setUserDataFollowsView(it)})
        viewModel.currentUser.observe(viewLifecycleOwner, Observer { setDataView(it) })
        viewModel.showToast.observe(viewLifecycleOwner, Observer { toast(R.string.error_toast) })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { updateLoadingState(it) })
    }


}