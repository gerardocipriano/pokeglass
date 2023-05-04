package com.example.pokeglass.local.teamroomdatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class TeamEntity(
    @PrimaryKey val name: String,
    val spriteUrl: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int
)