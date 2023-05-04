package com.example.pokeglass.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeglass.R
import com.example.pokeglass.data.TeamRepository
import com.example.pokeglass.local.teamroomdatabase.entities.TeamEntity
import com.squareup.picasso.Picasso

class TeamAdapter(
    private var teamList: List<TeamEntity>,
    private val repository: TeamRepository,
    private val onDeletePokemonListener: OnDeletePokemonListener
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spriteImageView = itemView.findViewById<ImageView>(R.id.spriteImageView)
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val statsTextView = itemView.findViewById<TextView>(R.id.statsTextView)
        val deleteButton = itemView.findViewById<Button>(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val teamEntity = teamList[position]
        holder.nameTextView.text = teamEntity.name
        holder.statsTextView.text = "HP: ${teamEntity.hp} Atk: ${teamEntity.attack} Def: ${teamEntity.defense} Spd: ${teamEntity.speed}"
        holder.deleteButton.setOnClickListener {
            repository.deleteTeamMember(teamEntity.name)
            onDeletePokemonListener.onDeletePokemon(teamEntity)
        }
        Picasso.get().load(teamEntity.spriteUrl).into(holder.spriteImageView)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    fun setTeamList(teamList: List<TeamEntity>) {
        this.teamList = teamList
        notifyDataSetChanged()
    }
}