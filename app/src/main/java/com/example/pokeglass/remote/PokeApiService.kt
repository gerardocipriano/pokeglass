package com.example.pokeglass.remote

import com.example.pokeglass.remote.models.PokemonResponse
import retrofit2.http.GET

interface PokeApiService {
    @GET("pokemon")
    suspend fun getAllPokemon(): PokemonResponse
}
