package com.romanuriel.coroutines.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.romanuriel.coroutines.adapter.CharactersAdapter
import com.romanuriel.coroutines.databinding.FragmentListCharactersBinding
import com.romanuriel.coroutines.viewmodel.ViewModelCharacters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCharactersFragment : Fragment() {
    private lateinit var binding : FragmentListCharactersBinding
    private val viewModel : ViewModelCharacters by viewModels()
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersAdapter = CharactersAdapter()

        with(binding) {
            recyclerCharacters.hasFixedSize()
            recyclerCharacters.layoutManager = LinearLayoutManager(requireContext())
            recyclerCharacters.adapter = charactersAdapter
        }
        charactersAdapter.onItemClick = {
            val action = ListCharactersFragmentDirections.actionListCharactersFragmentToInfoCharacterFragment(it!!)
            findNavController().navigate(action)
        }
        viewModel.mutableLiveDataListCharacters.observe(viewLifecycleOwner){
            charactersAdapter.setCaracters(it!!)
        }
        viewModel.getListCharacter()
    }

}