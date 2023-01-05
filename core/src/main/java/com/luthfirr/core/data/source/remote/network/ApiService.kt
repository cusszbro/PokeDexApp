package com.luthfirr.core.data.source.remote.network

import com.luthfirr.core.data.source.remote.response.DetailResponse
import com.luthfirr.core.data.source.remote.response.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon")
    fun getPokemonList(): Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun getPokemonDetail(
        @Path("name") name : String
    ): Call<DetailResponse>
}