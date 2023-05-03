package com.example.pokeglass.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.pokeglass.R
import com.example.pokeglass.data.TeamRepository
import com.example.pokeglass.local.teamlocalservice.RoomTeamLocalService
import com.example.pokeglass.local.teamlocalservice.TeamLocalService
import com.example.pokeglass.local.teamroomdatabase.daos.TeamDao
import com.example.pokeglass.local.teamroomdatabase.daos.TeamDao_Impl

import kotlinx.coroutines.launch

class TeamActivity : AppCompatActivity() {
    private lateinit var viewModel: TeamActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val teamDao: TeamDao = TeamDao()
        val teamLocalService: TeamLocalService = RoomTeamLocalService(teamDao)
        val repository = TeamRepository(teamLocalService)
        val viewModelFactory = TeamActivityViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TeamActivityViewModel::class.java]
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.team.collect { team ->
                    for (i in 0 until 6) {
                        val boxId = resources.getIdentifier("team_member_box_$i", "id", packageName)
                        val boxView = findViewById<View>(boxId)
                        if (i < team.size) {
                            val teamMember = team[i]
                            val pokemonSpriteImageView =
                                boxView.findViewById<ImageView>(R.id.pokemon_sprite_image_view)
                            val pokemonNameTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_name_text_view)
                            val pokemonHpTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_hp_text_view)
                            val pokemonAttackTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_attack_text_view)
                            val pokemonDefenseTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_defense_text_view)
                            val pokemonSpeedTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_speed_text_view)
                            val removeButton = boxView.findViewById<ImageButton>(R.id.remove_button)

                            // Load Pokemon sprite using Picasso
                            /*Picasso.get()
                                .load(teamMember.spriteUrl)
                                .into(pokemonSpriteImageView)*/

                            pokemonNameTextView.text = teamMember.name
                            pokemonHpTextView.text = "HP: ${teamMember.hp}"
                            pokemonAttackTextView.text = "Attack: ${teamMember.attack}"
                            pokemonDefenseTextView.text = "Defense: ${teamMember.defense}"
                            pokemonSpeedTextView.text = "Speed: ${teamMember.speed}"
                            removeButton.setOnClickListener {
                                viewModel.deleteByName(teamMember.name)
                            }
                        } else {
                            // Clear box data and hide remove button
                            val pokemonSpriteImageView =
                                boxView.findViewById<ImageView>(R.id.pokemon_sprite_image_view)
                            val pokemonNameTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_name_text_view)
                            val pokemonHpTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_hp_text_view)
                            val pokemonAttackTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_attack_text_view)
                            val pokemonDefenseTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_defense_text_view)
                            val pokemonSpeedTextView =
                                boxView.findViewById<TextView>(R.id.pokemon_speed_text_view)
                            val removeButton = boxView.findViewById<ImageButton>(R.id.remove_button)

                            pokemonSpriteImageView.setImageDrawable(null)
                            pokemonNameTextView.text = ""
                            pokemonHpTextView.text = ""
                            pokemonAttackTextView.text = ""
                            pokemonDefenseTextView.text = ""
                            pokemonSpeedTextView.text = ""
                            removeButton.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}