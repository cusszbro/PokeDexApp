package com.luthfirr.pokedexapp.mylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.luthfirr.core.data.source.local.entity.PokemonEntity
import com.luthfirr.core.data.source.local.room.PokemonDao
import com.luthfirr.core.data.source.local.room.PokemonDatabase

class MyPokemonListViewmodel(application: Application) : AndroidViewModel(application) {

    private var pokemonDao: PokemonDao?
    private var pokemonDb: PokemonDatabase?

    init {
        pokemonDb = PokemonDatabase.getDatabase(application)
        pokemonDao = pokemonDb?.pokemonDao()
    }

    fun getMyPokemonList(): LiveData<List<PokemonEntity>>? {
        return pokemonDao?.getMyList()
    }
}