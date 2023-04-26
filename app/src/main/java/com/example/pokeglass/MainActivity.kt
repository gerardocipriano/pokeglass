package com.example.pokeglass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeglass.adapters.PokemonAdapter
import com.example.pokeglass.remote.RemoteApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val service = RemoteApi.service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getAllPokemon()
            val allPokemon = response.results

            CoroutineScope(Dispatchers.Main).launch {
                val adapter = PokemonAdapter(allPokemon) { pokemon ->
                    // Aggiungi il Pokémon alla squadra qui
                }
                recyclerView.adapter = adapter
            }
        }
    }
}