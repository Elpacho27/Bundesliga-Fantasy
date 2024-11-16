package hr.ferit.patrikocelic.bundesligafantasy.data.mappers

import hr.ferit.patrikocelic.bundesligafantasy.data.models.AllPlayersResponse
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerUiModel

fun AllPlayersResponse.toPlayerUiModel(): List<PlayerUiModel> {
    return this.response
        .map { playerWithStatistics ->
            PlayerUiModel(
                id = playerWithStatistics.player.id,
                firstname = playerWithStatistics.player.firstname,
                lastname = playerWithStatistics.player.lastname,
                photo = playerWithStatistics.player.photo,
                position = playerWithStatistics.statistics.first().games.position,
                teamId = playerWithStatistics.statistics.first().team.id
            )
        }
}