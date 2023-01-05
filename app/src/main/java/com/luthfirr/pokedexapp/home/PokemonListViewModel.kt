package com.luthfirr.pokedexapp.home

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.luthfirr.core.data.source.remote.network.RetrofitClient
import com.luthfirr.core.data.source.remote.response.PokemonResponse
import com.luthfirr.core.data.source.remote.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListViewModel: ViewModel() {

    val listPokemon = MutableLiveData<ArrayList<ResultsItem>>()

    fun setPokemonList(){
        RetrofitClient.apiInstance
            .getPokemonList()
            .enqueue(object : Callback<PokemonResponse> {
                override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>
                ) {
                    if (response.isSuccessful) {
                        listPokemon.postValue(response.body()?.results)
                    }
                }

                override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getPokemonList() : LiveData<ArrayList<ResultsItem>> {
        return listPokemon
    }

    companion object {
        const val TAG = "PokemonListViewModel"
    }

}