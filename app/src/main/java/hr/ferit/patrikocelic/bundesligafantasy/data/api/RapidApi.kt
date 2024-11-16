package hr.ferit.patrikocelic.bundesligafantasy.data.api

import hr.ferit.patrikocelic.bundesligafantasy.data.models.AllPlayersResponse
import hr.ferit.patrikocelic.bundesligafantasy.data.models.FixtureResponseModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RapidApi {

    companion object {
        const val BASE_URL = "https://api-football-v1.p.rapidapi.com/v3/"
    }

    @GET("players")
    suspend fun getAllPlayersFromLeague(
        @Header("X-RapidAPI-Key") key: String = "80ad3051f5msh0a75d7b6c60a3a6p125233jsn56a1c670f31b",
        @Header("X-RapidAPI-Host") host: String = "api-football-v1.p.rapidapi.com",
        @Query("page") page: Int = 1,
        @Query("league") leagueId: Int = 78,
        @Query("season") season: Int = 2021,
    ): AllPlayersResponse

    @GET("fixtures")
    suspend fun getFixtureById(
        @Header("X-RapidAPI-Key") key: String = "80ad3051f5msh0a75d7b6c60a3a6p125233jsn56a1c670f31b",
        @Header("X-RapidAPI-Host") host: String = "api-football-v1.p.rapidapi.com",
        @Query("id") fixtureId: Int,
    ): FixtureResponseModel

    @GET("fixtures")
    suspend fun getFixturesByTeamId(
        @Header("X-RapidAPI-Key") key: String = "80ad3051f5msh0a75d7b6c60a3a6p125233jsn56a1c670f31b",
        @Header("X-RapidAPI-Host") host: String = "api-football-v1.p.rapidapi.com",
        @Query("team") teamId: Int,
        @Query("season") season: Int = 2021,
    ): FixtureResponseModel
}