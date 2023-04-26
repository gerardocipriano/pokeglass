package com.example.pokeglass
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeglass.adapters.PokemonAdapter
import com.example.pokeglass.remote.RemoteApi
import com.example.pokeglass.remote.models.Pokemon
import com.example.pokeglass.remote.models.PokemonResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val service = RemoteApi.service
    private var offset = 0
    private val limit = 12
    private var allPokemon: MutableList<Pokemon> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchEditText = findViewById<EditText>(R.id.search_edit_text)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PokemonAdapter(allPokemon) { pokemon ->
            // Aggiungi il Pokémon alla squadra qui
        }
        recyclerView.adapter = adapter

        // Carica i primi dati
        loadMorePokemon(adapter)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                val filteredPokemon = allPokemon.filter { it.name.contains(query, ignoreCase = true) }
                adapter.updateData(filteredPokemon)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun loadMorePokemon(adapter: PokemonAdapter) {
        CoroutineScope(Dispatchers.IO).launch {
            while (allPokemon.size < 151) {
                val response = service.getAllPokemon(limit, offset)
                val newPokemon = response.results.map { pokemonResult: PokemonResult ->
                    val pokemonResponse = service.getPokemon(pokemonResult.name)
                    val spriteUrl = pokemonResponse.sprites.front_default
                    Pokemon(pokemonResult.name, pokemonResult.url, spriteUrl)
                }

                allPokemon.addAll(newPokemon)
                offset += limit

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateData(allPokemon)
                }
            }
        }
    }
}