package com.example.pokeglass.data

import com.example.pokeglass.remote.PokeApiService
import com.example.pokeglass.remote.models.Pokemon
import com.example.pokeglass.remote.models.PokemonResult

/**
 * Repository for retrieving Pokemon data.
 *
 * This class is responsible for retrieving data from a remote service
 * and providing it to the ViewModel.
 *
 * @param service The remote service for retrieving data.
 */
class PokemonRepository(private val service: PokeApiService) {
/**
 * Retrieves a list of Pokemon from the remote service.
 *
 * @param limit The maximum number of Pokemon to retrieve.
 * @param offset The starting index for retrieving data.
 * @return The list of Pokemon retrieved from the remote service.
 */
suspend fun getAllPokemon(limit: Int, offset: Int): List<Pokemon> {
    val response = service.getAllPokemon(limit, offset)
    return response.results.map { pokemonResult: PokemonResult ->
        val pokemonResponse = service.getPokemon(pokemonResult.name)
        val spriteUrl = pokemonResponse.sprites.front_default
        Pokemon(pokemonResult.name, pokemonResult.url, spriteUrl)
    }
}
}