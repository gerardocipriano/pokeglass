package com.example.pokeglass.adapters

import com.example.pokeglass.local.teamroomdatabase.entities.TeamEntity

interface OnDeletePokemonListener {
    fun onDeletePokemon(teamEntity: TeamEntity)
}