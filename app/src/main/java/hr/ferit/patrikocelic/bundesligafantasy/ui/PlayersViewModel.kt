package hr.ferit.patrikocelic.bundesligafantasy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerUiModel
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerWithStatistics
import hr.ferit.patrikocelic.bundesligafantasy.data.models.Result
import hr.ferit.patrikocelic.bundesligafantasy.data.repository.FixtureRepository
import hr.ferit.patrikocelic.bundesligafantasy.data.repository.PlayersRepository
import hr.ferit.patrikocelic.bundesligafantasy.data.repository.ResultsRepository
import hr.ferit.patrikocelic.bundesligafantasy.ui.lineup.Position
import hr.ferit.patrikocelic.bundesligafantasy.utils.Constants.POSITION_ATTACKER
import hr.ferit.patrikocelic.bundesligafantasy.utils.Constants.POSITION_DEFENDER
import hr.ferit.patrikocelic.bundesligafantasy.utils.Constants.POSITION_GOALKEEPER
import hr.ferit.patrikocelic.bundesligafantasy.utils.Constants.POSITION_MIDFIELDER
import kotlinx.coroutines.launch

class PlayersViewModel : ViewModel() {

    private val _players = MutableLiveData<List<PlayerUiModel>>()
    val players: LiveData<List<PlayerUiModel>> get() = _players

    private val _playersLoading = MutableLiveData<Boolean>()
    val playersLoading: LiveData<Boolean> get() = _playersLoading

    private val _resultLoading = MutableLiveData<Boolean>()
    val resultLoading: LiveData<Boolean> get() = _resultLoading

    private val _selectedPlayers = MutableLiveData<MutableList<Pair<PlayerUiModel, Int>>>(mutableListOf())
    val selectedPlayers: LiveData<MutableList<Pair<PlayerUiModel, Int>>> get() = _selectedPlayers

    private val _totalPoints = MutableLiveData<Int>()
    val totalPoints: LiveData<Int> get() = _totalPoints

    private val _formation = MutableLiveData(R.layout.formation_4_4_2)
    val formation: LiveData<Int> get() = _formation

    fun observeResults() = ResultsRepository.observeResults()

    fun observePlayersByPosition(position: Position): LiveData<List<PlayerUiModel>> {
        return PlayersRepository.observePlayersByPosition(
            position.name.lowercase().replaceFirstChar { it.uppercase() }
        )
    }

    fun refreshPlayers() = viewModelScope.launch {
        _playersLoading.value = true
        PlayersRepository.getPlayers()
        _playersLoading.value = false
    }

    fun addSelectedPlayer(player: PlayerUiModel, viewId: Int) {
        _selectedPlayers.value!!
            .apply {
                val playerAlreadyAdded = any { it.first.id == player.id }
                if (playerAlreadyAdded) return
                val positionAlreadyAdded = any { it.second == viewId }
                if (positionAlreadyAdded) removeAll { it.second == viewId }
                add(Pair(player, viewId)) }
            .also { _selectedPlayers.value = it }
    }

    fun removeAllSelectedPlayers() {
        _selectedPlayers.value = mutableListOf()
    }

    fun setFormation(formation: Int) {
        if (_formation.value == formation) return
        _formation.value = formation
        removeAllSelectedPlayers()
    }

    fun getPointsForTeam() = viewModelScope.launch {
        _resultLoading.value = true

        val playersUiModel = selectedPlayers.value?.map { it.first } ?: emptyList()
        var totalPoints = 0

        for (player in playersUiModel) {
            val fixtureIds = FixtureRepository.getFixturesForTeam(player.teamId) ?: continue
            val fixtureIdsMutable = fixtureIds.toMutableList()
            var playersWithStatistics: List<PlayerWithStatistics>

            do {
                val randomFixtureId = fixtureIdsMutable.random()

                val fixture = FixtureRepository.fetchFixtureById(randomFixtureId)
                playersWithStatistics = fixture?.response?.flatMap { it.players.flatMap { it.players } } ?: emptyList()

                fixtureIdsMutable.filter { it != randomFixtureId }

                if (fixtureIdsMutable.isEmpty()) break
            } while (playersUiModel.isEmpty())

            if (playersWithStatistics.isEmpty()) continue

            val currentPlayerWithStatistics = playersWithStatistics.firstOrNull { it.player.id == player.id } ?: continue
            totalPoints += getPointsForPlayer(player, currentPlayerWithStatistics)
        }

        ResultsRepository.saveResult(Result(System.currentTimeMillis(), totalPoints))

        removeAllSelectedPlayers()
        _resultLoading.value = false
        _totalPoints.value = totalPoints
    }

    private fun getPointsForPlayer(
        player: PlayerUiModel,
        playerWithStatistics: PlayerWithStatistics
    ): Int {
        return when (player.position) {
            POSITION_GOALKEEPER -> 0
            POSITION_DEFENDER -> {
                val tackles = playerWithStatistics.statistics.firstOrNull()?.tackles?.total ?: 0
                tackles
            }
            POSITION_MIDFIELDER -> {
                val assists = playerWithStatistics.statistics.firstOrNull()?.goals?.assists ?: 0
                assists * 2
            }
            POSITION_ATTACKER -> {
                val goals = playerWithStatistics.statistics.firstOrNull()?.goals?.total ?: 0
                goals * 3
            }
            else -> 0
        }
    }
}