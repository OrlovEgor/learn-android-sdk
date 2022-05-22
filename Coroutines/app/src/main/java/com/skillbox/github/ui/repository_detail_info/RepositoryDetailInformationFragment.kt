package com.skillbox.github.ui.repository_detail_info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.databinding.FragmentRepositoryDetailInformationBinding
import com.skillbox.github.utils.toast

class RepositoryDetailInformationFragment :
    Fragment(R.layout.fragment_repository_detail_information) {

    private val binding: FragmentRepositoryDetailInformationBinding by viewBinding()
    private val args: RepositoryDetailInformationFragmentArgs by navArgs()
    private val viewModel: RepositoryDetailInformationViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.checkStar(args.nameArgs, args.ownerArgs)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDetailInfo()
        observeViewModelState()
        setButton()
    }

    private fun setDetailInfo() {
        binding.nameOWnerDetailInfoTextView.text = args.ownerArgs
        binding.nameDetailInfoTextView.text = args.nameArgs
        Glide.with(binding.detailInfoImageView)
            .load(args.avatarLinkArgs)
            .error(R.drawable.error_24)
            .into(binding.detailInfoImageView)
    }

    private fun observeViewModelState() {
        viewModel.isStared.observe(viewLifecycleOwner, Observer { checkStar(it) })
        viewModel.showToast.observe(viewLifecycleOwner)
        { toast(requireContext(), getString(R.string.error)) }
    }


    private fun checkStar(isStar: Boolean) {
        if (isStar) binding.starredDetailInfoButton.text = getString(R.string.Star)
        else binding.starredDetailInfoButton.text = getString(R.string.unstar)
    }

    private fun setButton() {

        binding.starredDetailInfoButton.setOnClickListener {
            if (viewModel.isStared.value == true) {
                viewModel.deleteStar(args.nameArgs, args.ownerArgs)
            } else {
                viewModel.addStar(args.nameArgs, args.ownerArgs)
            }
        }
    }
}
