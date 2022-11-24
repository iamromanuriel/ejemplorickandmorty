package com.romanuriel.coroutines.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.romanuriel.coroutines.databinding.FragmentDialogCharacterItemBinding
import com.romanuriel.coroutines.model.item.CharacterItem
import com.romanuriel.coroutines.utils.StateNetwork
import com.romanuriel.coroutines.viewmodel.ViewModelCharacters
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogFragmentCharacterItem: BottomSheetDialogFragment() {

    @Inject lateinit var stateNetwork: StateNetwork
    private var _binding : FragmentDialogCharacterItemBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelCharacters by viewModels()
    private var requestId: String? = null
    private var onCharacterItemRoomDb: ((CharacterItem?) -> Unit)? = null
    private var onCharacterItemApi: ((CharacterItem?) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogCharacterItemBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey"){key, bundle ->
            requestId = bundle.getString("bundleKey")
            val num = requestId?.toInt()
            if(stateNetwork.stateNetworkConnect(requireContext())){
                getItemCharacterApi(num!!)
                onCharacterItemApi ={characterApi ->
                    render(characterApi)
                    saveNewCharacterItem(characterApi!!)
                }
            }else{
                getItemCharacterRoomDb(num!!)
                onCharacterItemRoomDb ={
                    render(it)
                }
            }
        }
    }
    private fun getItemCharacterRoomDb(id: Int){
        viewModel.getCharacterItemRoomDb(id)
        viewModel.mutableLiveDataCharacterItemRoom.observe(viewLifecycleOwner) {
            onCharacterItemRoomDb?.invoke(it)

        }
    }

    private fun getItemCharacterApi(id: Int){
        viewModel.getItemCharacterApi(id.toString())
        viewModel.mutableLiveDataApiItemCharacter.observe(viewLifecycleOwner) {
            onCharacterItemApi?.invoke(it)
        }
    }


    private fun render(characterItem: CharacterItem?){
        binding.txtNameCharacter.text = characterItem?.name
        binding.txtStatus.text = characterItem?.status
        binding.txtType.text = characterItem?.type
        binding.txtGender.text = characterItem?.gender
        binding.txtCreated.text = characterItem?.created
        binding.txtOrigin.text = characterItem?.origin.toString()

        Glide.with(requireContext())
            .load(characterItem?.image)
            .into(binding.imageCharacter)
    }
    private fun saveNewCharacterItem(characterItem: CharacterItem){
        viewModel.addCharacter(characterItem)

    }


    //Test RxJava

    private fun printlnListCharacters(){
        viewModel.actionGetListCharacterItemObservable()
    }

    private fun printlnListCharacterItemMaybe(){
        viewModel.actionGetListCharacterItemMaybe()
    }

    private fun printlnListCharacterItemFlowable(){
        viewModel.actionGetListCharacterItemFlowable()
    }

    private fun addCharacteritem(characterItem: CharacterItem?) {
        viewModel.addCharacter(characterItem)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

















