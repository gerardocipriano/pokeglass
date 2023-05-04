package com.example.pokeglass.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pokeglass.data.TeamRepository
import com.example.pokeglass.local.teamlocalservice.RoomTeamLocalService
import com.example.pokeglass.local.teamroomdatabase.TeamRoomDatabase
import com.example.pokeglass.local.teamroomdatabase.entities.TeamEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TeamActivityViewModel(
    application: Application,
    val repository: TeamRepository
) : AndroidViewModel(application) {
    val teamList = repository.getAll()

    fun deleteTeamMember(teamEntity: TeamEntity) = viewModelScope.launch {
        repository.deleteTeamMember(teamEntity.name)
    }
}