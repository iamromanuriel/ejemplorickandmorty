package com.romanuriel.coroutines.data.repository


import com.romanuriel.coroutines.data.api.ApiService
import com.romanuriel.coroutines.data.api.ResultInfosApi
import com.romanuriel.coroutines.data.database.DaoCharacters
import com.romanuriel.coroutines.model.item.CharacterItem
import com.romanuriel.coroutines.utils.StateNetwork
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject


class RepositoryCharacters @Inject constructor(
    private val api: ApiService,
    private val daoCharacters: DaoCharacters,
    private val stateNetwork: StateNetwork){
    //Coroutines
    suspend fun getCharactersList() = withContext(Dispatchers.IO){
        if(stateNetwork.stateNetworkConnect() && daoCharacters.getListCharacter().isEmpty()){
            api.getInfoCharacter().execute().body()?.results?.forEach { item ->
                daoCharacters.insertCharacter(item)
            }
        }
        daoCharacters.getListCharacter()
    }

    suspend fun getItemCharacteritem(id: String) = withContext(Dispatchers.IO){
        api.getItemCharacter(id).execute().body()
    }

    suspend fun getCharacterItem(id: Int)= withContext(Dispatchers.IO){
            daoCharacters.getCharacteritem(id)
        }

    suspend fun deleteCharacterItem(id: Int) = withContext(Dispatchers.IO){
        daoCharacters.delete(id)
    }

    //Test RxJava
    fun getListCharacterTest(): Single<ResultInfosApi> {
        return api.getTestListCharacter()
    }

    fun getCharacteritem(id: Int): Single<CharacterItem>{
        return daoCharacters.getCharacterItem(id)
    }

    fun getListCharacterItemObservable(): Observable<List<CharacterItem>>{
        return daoCharacters.getListCharacterItemObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addCharacter(characterItem: CharacterItem?): Completable {
        return daoCharacters.addCharacter(characterItem)
    }
    fun getListCharacterRoom(): Single<List<CharacterItem>>{
        return daoCharacters.getListCharactersItems()
    }

    fun getListCharacterItemMaybe(): Maybe<List<CharacterItem>>{
        return daoCharacters.getListCharacterItemMaybe().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getListCharacterItemFlowable(): Flowable<List<CharacterItem>>{
        return daoCharacters.getListCharacterItemFlowable().subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

}