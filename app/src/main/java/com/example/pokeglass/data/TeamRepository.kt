package com.example.pokeglass.data

import com.example.pokeglass.local.teamlocalservice.TeamLocalService;
import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.entities.TeamEntity;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

public class TeamRepository(private val teamLocalService: TeamLocalService) {
    fun insertTeamMember(teamEntity: TeamEntity) = CoroutineScope(Dispatchers.IO).launch {
        val teamSize = teamLocalService.getTeamSize()
        if (teamSize < 6) {
            teamLocalService.insert(teamEntity)
        }
    }

    fun getAll(): Flow<List<TeamEntity>> {
        return teamLocalService.getAll()
    }

    fun deleteTeamMember(name: String) = CoroutineScope(Dispatchers.IO).launch {
        teamLocalService.deleteByName(name)
    }
}