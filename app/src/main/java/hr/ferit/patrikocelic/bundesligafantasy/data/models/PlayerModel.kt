package hr.ferit.patrikocelic.bundesligafantasy.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class AllPlayersResponse(
    val paging: Paging,
    val response: List<PlayerWithStatistics>
)

data class Paging(
    val current: Int,
    val total: Int
)

data class PlayerWithStatistics(
    val player: Player,
    val statistics: List<Statistics>
)

data class Player(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val photo: String
)

data class Statistics(
    val games: Games,
    val goals: Goals,
    val tackles: Tackles,
    val cards: Cards,
    val team: Team
)

data class Games(
    val position: String
)

data class Goals(
    val total: Int?,
    val assists: Int?
)

data class Tackles(
    val total: Int?
)

data class Cards(
    val yellow: Int,
    val red: Int
)

data class Team(
    val id: Int
)

@Entity(tableName = "player")
@Parcelize
data class PlayerUiModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val firstname: String,
    val lastname: String,
    val photo: String,
    val position: String,
    val teamId: Int
) : Parcelable

@Parcelize
data class SelectedPlayer(
    val player: PlayerUiModel,
    val viewId: Int
) : Parcelable
