package com.example.pokeglass.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeglass.R
import com.example.pokeglass.TeamApplication
import com.example.pokeglass.adapters.OnDeletePokemonListener
import com.example.pokeglass.adapters.TeamAdapter
import com.example.pokeglass.local.teamroomdatabase.entities.TeamEntity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TeamActivity : AppCompatActivity(), OnDeletePokemonListener {
    private lateinit var viewModel: TeamActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

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

    }

    override fun onDeletePokemon(teamEntity: TeamEntity) {
        viewModel.deleteTeamMember(teamEntity)
    }
}