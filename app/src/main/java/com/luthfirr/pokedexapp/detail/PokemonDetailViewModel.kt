package com.luthfirr.pokedexapp.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luthfirr.core.data.source.local.entity.PokemonEntity
import com.luthfirr.core.data.source.local.room.PokemonDao
import com.luthfirr.core.data.source.local.room.PokemonDatabase
import com.luthfirr.core.data.source.remote.network.RetrofitClient
import com.luthfirr.core.data.source.remote.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailViewModel(application : Application) : AndroidViewModel(application) {

    val listPokemonDetail = MutableLiveData<DetailResponse>()
    private var pokemonDao: PokemonDao?
    private var pokemonDb: PokemonDatabase?

    init {
        pokemonDb = PokemonDatabase.getDatabase(application)
        pokemonDao = pokemonDb?.pokemonDao()
    }

    fun setPokemonDetail(name : String){
        RetrofitClient.apiInstance
            .getPokemonDetail(name)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        listPokemonDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getPokemonDetail() : LiveData<DetailResponse>{
        return listPokemonDetail
    }

    fun addToMyList(id: Int, name: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val pokemon = PokemonEntity(
                id,
                name,
                url
            )
            pokemonDao?.addToMyList(pokemon)
        }
    }

    fun checkPokemon(id: Int) = pokemonDao?.checkList(id)

    fun removeFromMyList(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            pokemonDao?.removeFromMyList(id)
        }
    }

    companion object{
        const val TAG = "PokemonDetailViewModel"
    }
}