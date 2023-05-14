package com.example.pokeglass

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeglass.adapters.PokemonAdapter
import com.example.pokeglass.data.PokemonRepository
import com.example.pokeglass.remote.RemoteApi
import com.example.pokeglass.remote.models.Pokemon
import com.example.pokeglass.ui.PokedexActivityViewModel
import com.example.pokeglass.ui.PokedexActivityViewModelFactory
import com.google.android.material.navigation.NavigationView

class PokedexActivity : AppCompatActivity() {
    private lateinit var viewModel: PokedexActivityViewModel
    private var allPokemon: MutableList<Pokemon> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val service = RemoteApi.service
        val pokedexRepository = PokemonRepository(service)
        val viewModelFactory = PokedexActivityViewModelFactory(pokedexRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PokedexActivityViewModel::class.java]

        val searchEditText = findViewById<EditText>(R.id.search_edit_text)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val teamApplication = application as TeamApplication
        val teamRepository = teamApplication.getTeamRepository()
        val adapter = PokemonAdapter(allPokemon, teamRepository, pokedexRepository) { _ ->
            // Gestisci il clic su un elemento della lista dei Pokemon
        }
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

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_pokedex -> {
                    // Naviga verso la PokedexActivity
                }
                R.id.nav_team -> {
                    // Naviga verso la TeamActivity
                    val intent = Intent(this, TeamActivity::class.java)
                    startActivity(intent)
                }
            }
            val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
            drawerLayout.closeDrawers()
            true
        }

        toolbar.setNavigationOnClickListener {
            val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}