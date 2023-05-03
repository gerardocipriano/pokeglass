package com.example.pokeglass.ui

import androidx.lifecycle.ViewModel
import com.example.pokeglass.data.TeamRepository
import com.example.pokeglass.local.teamroomdatabase.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

class TeamActivityViewModel(private val repository: TeamRepository) : ViewModel() {
    val team: Flow<List<TeamEntity>> = repository.getAll()

    fun insert(teamMember: TeamEntity) {
        repository.insert(teamMember)
    }

    fun deleteByName(name: String) {
        repository.deleteByName(name)
    }
}