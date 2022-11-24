package com.romanuriel.coroutines.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.romanuriel.coroutines.adapter.CharactersAdapter
import com.romanuriel.coroutines.databinding.FragmentListCharactersBinding
import com.romanuriel.coroutines.model.item.CharacterItem
import com.romanuriel.coroutines.utils.StateNetwork
import com.romanuriel.coroutines.viewmodel.ViewModelCharacters
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListCharactersFragment : Fragment() {
    @Inject lateinit var stateNetwork: StateNetwork
    private var _binding : FragmentListCharactersBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ViewModelCharacters by viewModels()
    private lateinit var charactersAdapter: CharactersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(stateNetwork.stateNetworkConnect(requireContext())){
            getListCharacterItemApi()
        }else{
            getListCharacterItemRoom()
        }
    }

    private fun showDialogFragment(id: String){
        setFragmentResult("requestKey", bundleOf("bundleKey" to id))
        DialogFragmentCharacterItem().show(requireFragmentManager(),DialogFragmentCharacterItem::class.java.simpleName)
    }

    private fun getListCharacterItemRoom(){
        viewModel.actionGetListCharacterRoom()
        viewModel.mutableLiveDataListCharacterItemRoom.observe(viewLifecycleOwner, Observer { listCharacter: List<CharacterItem> ->
            initRecyclerView(listCharacter)
        })
    }

    private fun getListCharacterItemApi(){
        viewModel.getTestRxJavaCharacter()
        viewModel.mutableLiveDataApiListCharacter.observe(viewLifecycleOwner, Observer { listCharacter: List<CharacterItem> ->
            initRecyclerView(listCharacter)
        })
    }


    private fun initRecyclerView(listCharacter: List<CharacterItem>){
        charactersAdapter = CharactersAdapter(listCharacter)

        with(binding) {
            recyclerCharacters.hasFixedSize()
            recyclerCharacters.layoutManager = LinearLayoutManager(requireContext())
            recyclerCharacters.adapter = charactersAdapter
        }

        charactersAdapter.onItemClick = {
            showDialogFragment(it?.id.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}