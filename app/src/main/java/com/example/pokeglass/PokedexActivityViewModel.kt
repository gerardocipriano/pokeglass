package com.example.pokeglass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeglass.remote.models.Pokemon
import com.example.pokeglass.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for the PokedexActivity.
 *
 * This class is responsible for managing the data for the PokedexActivity,
 * including loading the list of Pokemon from a repository and exposing
 * the data to the activity via LiveData.
 *
 * @param repository The repository for retrieving data.
 */
class PokedexActivityViewModel(private val repository: PokemonRepository) : ViewModel() {
    // MutableLiveData for holding the list of Pokemon
    private val _pokemonList = MutableLiveData<List<Pokemon>>()

    // LiveData for exposing the list of Pokemon to the activity
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    // Offset for retrieving data from the repository
    private var offset = 0

    // Limit for retrieving data from the repository
    private val limit = 12

    // Total number of Pokemon loaded
    private var totalLoaded = 0

    // Total number of Pokemon in the Kanto Pokedex
    private var pokedexKantoSize = 163

    init {
        // Load initial data when the ViewModel is created
        loadMorePokemon()
    }

    /**
     * Loads more Pokemon from the repository.
     *
     * This method retrieves data from the repository and updates
     * the list of Pokemon exposed to the activity via LiveData. The
     * method continues to load data until a total of 150 Pokemon have
     * been loaded.
     */
    private fun loadMorePokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            while (totalLoaded < pokedexKantoSize) {
                val newPokemon = repository.getAllPokemon(limit, offset)
                offset += limit
                totalLoaded += newPokemon.size
                val remaining = (pokedexKantoSize - totalLoaded).coerceAtLeast(0)
                _pokemonList.postValue(newPokemon.take(remaining))
            }
        }
    }
}