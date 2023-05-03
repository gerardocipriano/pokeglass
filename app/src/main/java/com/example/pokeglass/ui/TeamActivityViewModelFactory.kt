package com.example.pokeglass.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokeglass.data.TeamRepository

class TeamActivityViewModelFactory (private val repository: TeamRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexActivityViewModel::class.java)) {
            return TeamActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}