package com.romanuriel.coroutines.data.api

import com.romanuriel.coroutines.model.item.CharacterItem
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService{
    @GET("character")
    fun getInfoCharacter(): Call<ResultInfosApi>

    @GET("character/{id}")
    fun getItemCharacter(@Path("id") id:String): Call<CharacterItem>

    //Test RxJava
    @GET("character")
    fun getTestListCharacter(): Single<ResultInfosApi>



}