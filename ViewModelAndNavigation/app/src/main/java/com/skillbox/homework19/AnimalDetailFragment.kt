package com.skillbox.homework19

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.homework19.DataClasses.Animal
import com.skillbox.homework19.databinding.FragmentDetailAnimalBinding

class AnimalDetailFragment : Fragment(R.layout.fragment_detail_animal) {

    private val binding: FragmentDetailAnimalBinding by viewBinding()

    private val args: AnimalDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailNameTextView.text = args.id.toString()
        binding.detailNameTextView.text = args.name
    }
}