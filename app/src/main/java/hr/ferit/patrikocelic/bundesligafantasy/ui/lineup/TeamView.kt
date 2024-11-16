package hr.ferit.patrikocelic.bundesligafantasy.ui.lineup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import coil.transform.CircleCropTransformation
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerUiModel
import hr.ferit.patrikocelic.bundesligafantasy.databinding.TeamViewBinding
import hr.ferit.patrikocelic.bundesligafantasy.utils.Constants.POSITION_ATTACKER
import hr.ferit.patrikocelic.bundesligafantasy.utils.Constants.POSITION_DEFENDER
import hr.ferit.patrikocelic.bundesligafantasy.utils.Constants.POSITION_GOALKEEPER
import hr.ferit.patrikocelic.bundesligafantasy.utils.Constants.POSITION_MIDFIELDER

class TeamView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding = TeamViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private var onPlayerClickListener: ((Position, Int) -> Unit)? = null
    private var currentFormation: Int = R.layout.formation_4_4_2

    init {
        binding.teamWrapper.background = FootballFieldDrawable()
        binding.playersContainer.addView(LayoutInflater.from(context).inflate(R.layout.formation_4_4_2, null, false))
        setListeners()
    }

    private fun setListeners() {
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player1)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player2)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player3)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player4)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player5)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player6)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player7)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player8)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player9)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player10)?.setOnClickListener { onPlayerClick(it) }
        binding.playersContainer.findViewById<ConstraintLayout>(R.id.player11)?.setOnClickListener { onPlayerClick(it) }
    }

    private fun onPlayerClick(view: View) {
        val parentContainer = view.parent as ViewGroup
        val position = when (parentContainer.id) {
            R.id.goalkeeperContainer -> Position.GOALKEEPER
            R.id.defenceContainer -> Position.DEFENDER
            R.id.middleContainer -> Position.MIDFIELDER
            R.id.attackContainer -> Position.ATTACKER
            else -> throw IllegalArgumentException("Not supported position")
        }

        onPlayerClickListener?.invoke(position, view.id)
    }

    fun setOnPlayerClickListener(listener: (Position, Int) -> Unit) {
        onPlayerClickListener = listener
    }

    fun setPlayer(playerUiModel: PlayerUiModel, viewId: Int) {
        binding.playersContainer.findViewById<ConstraintLayout>(viewId)
            ?.let {
                it.findViewById<ImageView>(R.id.playerImage)?.load(playerUiModel.photo) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                it.findViewById<TextView>(R.id.playerName)?.text = context.getString(
                    R.string.players_name_placeholder,
                    playerUiModel.firstname,
                    playerUiModel.lastname
                )
            }
    }

    fun reset() {
        val currentFormation = getCurrentFormationId()
        binding.playersContainer.removeAllViews()
        val layoutId = when (currentFormation) {
            R.id.formation442 -> R.layout.formation_4_4_2
            R.id.formation433 -> R.layout.formation_4_3_3
            else -> null
        }
        layoutId?.let {
            binding.playersContainer.addView(LayoutInflater.from(context).inflate(it, null, false))
        }
        setListeners()
    }

    @IdRes
    private fun getCurrentFormationId(): Int? {
        return when {
            binding.playersContainer.findViewById<ConstraintLayout>(R.id.formation442) != null -> R.id.formation442
            binding.playersContainer.findViewById<ConstraintLayout>(R.id.formation433) != null -> R.id.formation433
            else -> null
        }
    }

    fun changeFormation(@LayoutRes formationId: Int) {
        if (formationId == currentFormation) return
        val formation442 = binding.playersContainer.findViewById<ConstraintLayout>(R.id.formation442) != null
        val formation433 = binding.playersContainer.findViewById<ConstraintLayout>(R.id.formation433) != null

        if (
            formation442 && formationId == R.layout.formation_4_4_2
            || formation433 && formationId == R.layout.formation_4_3_3
        ) return

        binding.playersContainer.removeAllViews()
        binding.playersContainer.addView(LayoutInflater.from(context).inflate(formationId, null, false))
        setListeners()
    }
}

enum class Position(name: String) {
    GOALKEEPER(POSITION_GOALKEEPER),
    DEFENDER(POSITION_DEFENDER),
    MIDFIELDER(POSITION_MIDFIELDER),
    ATTACKER(POSITION_ATTACKER)
}