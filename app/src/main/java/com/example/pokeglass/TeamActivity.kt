package com.example.pokeglass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeglass.adapters.OnDeletePokemonListener
import com.example.pokeglass.adapters.TeamAdapter
import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.entities.TeamEntity
import com.example.pokeglass.ui.TeamActivityViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TeamActivity : AppCompatActivity(), OnDeletePokemonListener {
    private lateinit var viewModel: TeamActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val teamApplication = application as TeamApplication
        val repository = teamApplication.getTeamRepository()
        viewModel = TeamActivityViewModel(application, repository)

        val recyclerView = findViewById<RecyclerView>(R.id.teamRecyclerView)
        val adapter = TeamAdapter(emptyList(), viewModel.repository, this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.teamList.collectLatest { teamList: List<TeamEntity> ->
                adapter.setTeamList(teamList)
            }
        }
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_pokedex -> {
                    // Naviga verso la PokedexActivity
                    val intent = Intent(this, PokedexActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_team -> {
                    // Naviga verso la TeamActivity

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

    override fun onDeletePokemon(teamEntity: TeamEntity) {
        viewModel.deleteTeamMember(teamEntity)
    }
}