package com.example.pokeglass.local.teamlocalservice

import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.daos.TeamDao
import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

class RoomTeamLocalService(private val teamDao: TeamDao) : TeamLocalService {
    override fun insert(teamMember: TeamEntity) {
        teamDao.insert(teamMember)
    }

    override fun getAll(): Flow<List<TeamEntity>> {
        return teamDao.getAll()
    }

    override fun deleteByName(name: String) {
        teamDao.deleteByName(name)
    }
    override fun getTeamSize(): Int {
        return teamDao.getTeamSize()
    }
}