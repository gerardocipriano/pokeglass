package com.example.pokeglass.local.teamlocalservice

import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

interface TeamLocalService {
    fun insert(teamMember: TeamEntity)
    fun getAll(): Flow<List<TeamEntity>>
    fun deleteByName(name: String)

    fun getTeamSize(): Int
}