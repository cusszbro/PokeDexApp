package com.luthfirr.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.luthfirr.core.data.source.local.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Insert
    fun addToMyList(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM pokemonEntity")
    fun getMyList(): LiveData<List<PokemonEntity>>

    @Query("SELECT count(*) FROM pokemonEntity WHERE pokemonEntity.name = :id")
    fun checkList(id: Int): Int

    @Query("DELETE FROM pokemonEntity WHERE pokemonEntity.name = :id")
    fun removeFromMyList(id: Int): Int
}