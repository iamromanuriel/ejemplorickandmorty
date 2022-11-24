package com.romanuriel.coroutines.data.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.romanuriel.coroutines.model.item.CharacterItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface DaoCharacters {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDatabase(characterItem: CharacterItem?): Completable

    @Query("SELECT * FROM tb_characters")
    fun getListCharactersItems(): Single<List<CharacterItem>>

    @Query("SELECT * FROM tb_characters WHERE id = :id")
    fun getCharacterItem(id: Int): Single<CharacterItem>

    @Query("SELECT * FROM tb_characters")
    fun getListCharacterItemObservable(): Observable<List<CharacterItem>>

    @Query("SELECT * FROM tb_characters")
    fun getListCharacterItemMaybe() : Maybe<List<CharacterItem>>

    @Query("SELECT * FROM tb_characters")
    fun getListCharacterItemFlowable(): Flowable<List<CharacterItem>>

}