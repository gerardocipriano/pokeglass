package com.example.pokeglass
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeglass.adapters.PokemonAdapter
import com.example.pokeglass.remote.RemoteApi
import com.example.pokeglass.remote.models.Pokemon
import com.example.pokeglass.repository.PokemonRepository

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PokedexActivityViewModel
    private var allPokemon: MutableList<Pokemon> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RemoteApi.service
        val repository = PokemonRepository(service)
        val viewModelFactory = MainActivityViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PokedexActivityViewModel::class.java)

        val searchEditText = findViewById<EditText>(R.id.search_edit_text)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PokemonAdapter(allPokemon) { pokemon -> }
        recyclerView.adapter = adapter

        viewModel.pokemonList.observe(this, Observer { newPokemon ->
            allPokemon.addAll(newPokemon)
            adapter.updateData(allPokemon)
        })

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
}

class MainActivityViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexActivityViewModel::class.java)) {
            return PokedexActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}