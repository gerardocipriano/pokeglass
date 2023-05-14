package com.example.pokeglass.local.teamlocalservice.teamroomdatabase.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(teamMember: TeamEntity)

    @Query("SELECT * FROM team_table")
    fun getAll(): Flow<List<TeamEntity>>

    @Query("DELETE FROM team_table WHERE name = :name")
    fun deleteByName(name: String)
    @Query("SELECT COUNT(*) FROM team_table")
    fun getTeamSize(): Int
}