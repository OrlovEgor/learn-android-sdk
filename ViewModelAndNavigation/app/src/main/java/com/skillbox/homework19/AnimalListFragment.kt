package com.skillbox.homework19

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.homework19.Adapter.AnimalAdapter
import com.skillbox.homework19.Utils.autoCleared
import com.skillbox.homework19.databinding.FragmentListAnimalBinding
import jp.wasabeef.recyclerview.animators.ScaleInAnimator

class AnimalListFragment : Fragment(R.layout.fragment_list_animal) {

    private val binding: FragmentListAnimalBinding by viewBinding()
    private var animalAdapter: AnimalAdapter by autoCleared<AnimalAdapter>()
    private val animalViewModel: AnimalViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModelState()
        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_animalListFragment_to_animalDialogFragment)
        }
    }

    private fun initList() {
        animalAdapter = AnimalAdapter({ position: Int -> deleteAnimal(position) },
            { id, name ->
                val action =
                    AnimalListFragmentDirections.actionAnimalListFragmentToAnimalDetailFragment(
                        id,
                        name
                    )
                findNavController().navigate(action)
            })
        with(binding.listAnimalView) {
            adapter = animalAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = ScaleInAnimator()
        }
    }

    private fun deleteAnimal(position: Int) {
        animalViewModel.deleteAnimal(position)
    }

    private fun observeViewModelState() {
        animalViewModel.animalLiveData.observe(viewLifecycleOwner) { newAnimals ->
            animalAdapter.items = newAnimals
        }
        animalViewModel.showToast
            .observe(viewLifecycleOwner) {
                Toast.makeText(
                    requireContext(), getString(R.string.remove_animal), Toast.LENGTH_SHORT
                ).show()
            }
    }
}
