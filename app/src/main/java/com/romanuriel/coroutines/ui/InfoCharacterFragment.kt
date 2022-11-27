package com.romanuriel.coroutines.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.romanuriel.coroutines.databinding.FragmentInfoCharacterBinding
import com.romanuriel.coroutines.model.item.CharacterItem
import com.romanuriel.coroutines.viewmodel.ViewModelCharacters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoCharacterFragment : Fragment() {
    private lateinit var binding: FragmentInfoCharacterBinding
    private val viewModel : ViewModelCharacters by viewModels()
    private lateinit var navController: NavController
    private val argCharacter: InfoCharacterFragmentArgs by navArgs<InfoCharacterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoCharacterBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        render(argCharacter.argCharacter)
    }

    private fun render(item: CharacterItem){
        with(binding){
            txtNameIndicator.text = item.name
            statusCharacter.text = item.status
            typeCharacter.text = item.type
            generCharacter.text = item.gender
            dateCreateCharacter.text = item.created
            originCharacter.text = item.origin.nameOrigin
            locationCharacter.text = item.location.nameLocation
            Glide.with(requireContext())
                .load(item.image)
                .into(imageCharacter)
        }
    }
}