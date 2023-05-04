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
import com.example.pokeglass.remote.models.Pokemon
import com.squareup.picasso.Picasso

/**
 * Adapter for displaying a list of Pokemon in a RecyclerView.
 *
 * @property pokemonList The list of Pokemon to display.
 * @property onAddClicked A lambda function that is triggered when the "Add" button is clicked for a Pokemon.
 */
class PokemonAdapter(
    private var pokemonList: List<Pokemon>,
    private val teamRepository: TeamRepository,
    private val onAddClicked: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    /**
     * ViewHolder for displaying a single Pokemon item.
     *
     * @property nameTextView The TextView for displaying the name of the Pokemon.
     * @property addButton The Button for adding the Pokemon.
     * @property spriteImageView The ImageView for displaying the sprite of the Pokemon.
     */
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.pokemon_name)
        val addButton: Button = itemView.findViewById(R.id.add_button)
        val spriteImageView: ImageView = itemView.findViewById(R.id.pokemon_sprite)
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should update the contents of the ViewHolder to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.nameTextView.text = pokemon.name
        holder.addButton.setOnClickListener {
            val teamEntity = TeamEntity(
                name = pokemon.name,
                spriteUrl = pokemon.spriteUrl,
                hp = 0,
                attack = 0,
                defense = 0,
                speed = 0
            )
            teamRepository.insertTeamMember(teamEntity)
        }
        Picasso.get().load(pokemon.spriteUrl).into(holder.spriteImageView)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return pokemonList.size
    }

    /**
     * Updates the list of Pokemon and notifies the adapter that the data has changed.
     *
     * @param newPokemonList The new list of Pokemon to display.
     */
    fun updateData(newPokemonList: List<Pokemon>) {
        pokemonList = newPokemonList
        notifyDataSetChanged()
    }
}
