package com.romanuriel.coroutines.data.repository


import com.romanuriel.coroutines.data.api.ApiService
import com.romanuriel.coroutines.data.api.ResultInfosApi
import com.romanuriel.coroutines.data.database.DaoCharacters
import com.romanuriel.coroutines.model.item.CharacterItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject


class RepositoryCharacters @Inject constructor(private val api: ApiService, private val daoCharacters: DaoCharacters){
    //Coroutines
    suspend fun getCharactersTestCoroutines() = withContext(Dispatchers.IO){
        api.getInfoCharacter().execute().body()?.results
    }

    suspend fun getItemCharacteritem(id: String) = withContext(Dispatchers.IO){
        api.getItemCharacter(id).execute().body()
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
        return daoCharacters.addDatabase(characterItem)
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