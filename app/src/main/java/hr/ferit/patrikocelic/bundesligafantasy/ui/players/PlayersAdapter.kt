package hr.ferit.patrikocelic.bundesligafantasy.ui.players

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerUiModel
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.databinding.PlayerListItemBinding

class PlayersAdapter(
    private val onItemClick: (PlayerUiModel) -> Unit
) : ListAdapter<PlayerUiModel, PlayersAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PlayerUiModel>() {
            override fun areItemsTheSame(
                oldItem: PlayerUiModel,
                newItem: PlayerUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PlayerUiModel,
                newItem: PlayerUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        PlayerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PlayerListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(player: PlayerUiModel) {
            binding.playerImage.load(player.photo) {
                crossfade(true)
                placeholder(R.drawable.ic_player)
                transformations(CircleCropTransformation())
            }
            binding.playerName.text = binding.root.context.getString(
                R.string.players_name_placeholder,
                player.firstname,
                player.lastname
            )
        }
    }


}