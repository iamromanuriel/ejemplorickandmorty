package com.romanuriel.coroutines.data.api



import com.romanuriel.coroutines.model.Info
import com.romanuriel.coroutines.model.item.CharacterItem

data class ResultInfosApi (
    val info: Info,
    val results: List<CharacterItem>
)
