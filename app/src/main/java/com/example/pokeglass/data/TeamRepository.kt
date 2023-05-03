package com.example.pokeglass.data

import com.example.pokeglass.local.teamlocalservice.TeamLocalService;
import com.example.pokeglass.local.teamroomdatabase.entities.TeamEntity;
import kotlinx.coroutines.flow.Flow

public class TeamRepository(private val teamLocalService: TeamLocalService) {
    fun insert(teamEntity:TeamEntity) {
        teamLocalService.insert(teamEntity)
    }

    fun getAll(): Flow<List<TeamEntity>> {
        return teamLocalService.getAll()
    }

    fun deleteByName(name: String) {
        teamLocalService.deleteByName(name)
    }
}