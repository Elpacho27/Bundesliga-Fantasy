package hr.ferit.patrikocelic.bundesligafantasy.data.repository

import android.util.Log
import hr.ferit.patrikocelic.bundesligafantasy.data.api.Retrofit
import hr.ferit.patrikocelic.bundesligafantasy.data.models.FixtureResponseModel

object FixtureRepository {

    private val TAG = "[DEBUG] FixtureRepository"

    suspend fun getFixturesForTeam(teamId: Int): List<Int>? {
        return fetchFixtureTeamId(teamId)
            ?.response
            ?.filter { it.fixture.status.short == "FT" }
            ?.map { it.fixture.id }
    }

    private suspend fun fetchFixtureTeamId(teamId: Int): FixtureResponseModel? {
        return try {
            Retrofit.instance.getFixturesByTeamId(teamId = teamId)
        } catch (e: Exception) {
            Log.e(TAG, "fetchFixtureTeamId: $e")
            null
        }
    }

    suspend fun fetchFixtureById(fixtureId: Int): FixtureResponseModel? {
        return try {
            Retrofit.instance.getFixtureById(fixtureId = fixtureId)
        } catch (e: Exception) {
            null
        }
    }
}