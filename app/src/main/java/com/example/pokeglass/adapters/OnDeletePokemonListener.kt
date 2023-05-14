package com.example.pokeglass.adapters

import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.entities.TeamEntity

interface OnDeletePokemonListener {
    fun onDeletePokemon(teamEntity: TeamEntity)
}