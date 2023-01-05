package com.luthfirr.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pokemonEntity")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String
): Serializable
