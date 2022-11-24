package com.romanuriel.coroutines.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romanuriel.coroutines.model.item.CharacterItem
import com.romanuriel.coroutines.data.repository.RepositoryCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelCharacters @Inject constructor(
    private val repo : RepositoryCharacters
): ViewModel() {

    val mutableLiveDataListCharacters = MutableLiveData<List<CharacterItem>?>()
    val mutableLiveDataApiItemCharacter = MutableLiveData<CharacterItem?>()
    val mutableLiveDataApiListCharacter = MutableLiveData<List<CharacterItem>>()
    //Database
    val mutableLiveDataListCharacterItemRoom = MutableLiveData<List<CharacterItem>>()
    val mutableLiveDataCharacterItemRoom = MutableLiveData<CharacterItem>()



    var characterJob: Job? = null


    fun getItemCharacterApi(id: String) = viewModelScope.launch {
        val itemCharacter = repo.getItemCharacteritem(id)
        mutableLiveDataApiItemCharacter.postValue(itemCharacter)
    }



    //Test RxJava
    private val disposable = CompositeDisposable()
    fun getTestRxJavaCharacter() {
        disposable.add(repo.getListCharacterTest().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()).subscribe({
               mutableLiveDataApiListCharacter.postValue(it.results)
        },{
            println(it.message)
        }))
    }

    fun getCharacterItemRoomDb(id: Int){
        disposable.add(repo.getCharacteritem(id).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()).subscribe({
            mutableLiveDataCharacterItemRoom.postValue(it)
        },{
            println(it.message)
        }))
    }

    //Database

    fun addCharacter(characterItem: CharacterItem?) {
        repo.addCharacter(characterItem).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    fun actionGetListCharacterRoom(){
        disposable.add(repo.getListCharacterRoom().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
             mutableLiveDataListCharacterItemRoom.postValue(it)
        },{
            println(it.message)
        }))
    }

    fun actionGetListCharacterItemObservable(){
        repo.getListCharacterItemObservable().subscribe({list ->
                              list.forEach {
                                  println("Este es un $it")
                              }
        },{
            println(it.message)
        })
    }

    fun actionGetListCharacterItemMaybe(){
        repo.getListCharacterItemMaybe().subscribe({
             it.forEach { it-> println(it) }
        },{
            println(it.message)
        })
    }

    fun actionGetListCharacterItemFlowable(){
        repo.getListCharacterItemFlowable().subscribe({
            it.forEach { it-> println(it) }
        },{
            println("Error en ${it.message}")
        })
    }

}