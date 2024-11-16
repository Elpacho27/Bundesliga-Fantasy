package hr.ferit.patrikocelic.bundesligafantasy.data.repository

import android.util.Log
import hr.ferit.patrikocelic.bundesligafantasy.data.api.Retrofit
import hr.ferit.patrikocelic.bundesligafantasy.data.db.AppDatabase
import hr.ferit.patrikocelic.bundesligafantasy.data.models.AllPlayersResponse
import hr.ferit.patrikocelic.bundesligafantasy.data.mappers.toPlayerUiModel

object PlayersRepository {

    suspend fun getPlayers() {
        val initialResponse = fetchPlayers(1) ?: return
        val currentPage = initialResponse.paging.current
        val lastPage = initialResponse.paging.total
        val fetchMore = currentPage == 1 && currentPage < lastPage

        val otherResponse = if (!fetchMore) {
            emptyList()
        } else {
            ((currentPage + 1)..lastPage)
                .mapNotNull { page -> fetchPlayers(page) }
        }

        val total = listOf(initialResponse) + otherResponse
        total
            .flatMap { it.toPlayerUiModel() }
            .distinctBy { it.id }
            .let { AppDatabase.getInstance().getPlayerDao().replaceData(it) }
    }

    private val TAG = "Fetch players"

    private suspend fun fetchPlayers(page: Int): AllPlayersResponse? {
        Log.d(TAG, "fetchPlayers: $page")
        return try {
            Retrofit.instance.getAllPlayersFromLeague(page = page)
        } catch (e: Exception) {
            Log.e("Fetch players", "Error fetching players", e)
            null
        }
    }

    fun observePlayersByPosition(position: String) = AppDatabase.getInstance().getPlayerDao().getPlayersByPosition(position)
}