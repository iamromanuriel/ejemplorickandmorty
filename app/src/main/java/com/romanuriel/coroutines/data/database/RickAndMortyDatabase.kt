package com.romanuriel.coroutines.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romanuriel.coroutines.model.item.CharacterItem

@Database(
    entities = [CharacterItem::class],
    version = 1,
exportSchema = true)
abstract class RickAndMortyDatabase: RoomDatabase() {
    abstract fun getCharactersDao(): DaoCharacters
}