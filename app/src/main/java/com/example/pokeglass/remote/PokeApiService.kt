package com.example.pokeglass.remote

import com.example.pokeglass.remote.RemoteApi.service
import com.example.pokeglass.remote.models.Pokemon
import com.example.pokeglass.remote.models.PokemonDetailsResponse
import com.example.pokeglass.remote.models.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int = 163,
        @Query("offset") offset: Int = 0
    ): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name: String
    ): PokemonDetailsResponse
}
