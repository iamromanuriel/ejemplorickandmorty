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
    val mutableLiveDataItemCharacter = MutableLiveData<CharacterItem?>()



    fun getListCharacter()= viewModelScope.launch{
        val result = repo.getCharactersList()
        mutableLiveDataListCharacters.postValue(result)
    }

    fun getItemCharacterApi(id: String) = viewModelScope.launch {
        val itemCharacter = repo.getItemCharacteritem(id)
        mutableLiveDataItemCharacter.postValue(itemCharacter)
    }

    var characterJob: Job? = null
    fun getItemCharacter(id: Int) = viewModelScope.launch{
        val response = repo.getCharacterItem(id)
        mutableLiveDataItemCharacter.postValue(response)
    }

    fun deleteCharacterItem(id: Int) = viewModelScope.launch{
        repo.deleteCharacterItem(id)
    }


    //Test RxJava
    private val disposable = CompositeDisposable()
    fun getTestRxJavaCharacter() {
        disposable.add(repo.getListCharacterTest().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()).subscribe({
               mutableLiveDataListCharacters.postValue(it.results)
        },{
            println(it.message)
        }))
    }

    fun getCharacterItemRoomDb(id: Int){
        disposable.add(repo.getCharacteritem(id).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()).subscribe({
            mutableLiveDataItemCharacter.postValue(it)
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
             mutableLiveDataListCharacters.postValue(it)
        },{
            println(it.message)
        }))
    }

    fun actionGetListCharacterItemObservable(){
        disposable.add(
            repo.getListCharacterItemObservable().subscribe({list ->
                list.forEach {
                    println("Este es un $it")
                }
            },{
                println(it.message)
            })
        )

    }

    fun actionGetListCharacterItemMaybe(){
        disposable.add(
            repo.getListCharacterItemMaybe().subscribe({
                it.forEach { it-> println(it) }
            },{
                println(it.message)
            })
        )
    }

    fun actionGetListCharacterItemFlowable(){
        disposable.add(
            repo.getListCharacterItemFlowable().subscribe({
                it.forEach { it-> println(it) }
            },{
                println("Error en ${it.message}")
            })
        )

    }

}