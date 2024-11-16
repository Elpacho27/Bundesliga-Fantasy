package hr.ferit.patrikocelic.bundesligafantasy.data.models

data class FixtureResponseModel(
    val response: List<Response>
)

data class Response(
    val fixture: Fixture,
    val players: List<PlayerFixture>
)

data class PlayerFixture(
    val players: List<PlayerWithStatistics>
)

data class Fixture(
    val id: Int,
    val status: Status,
)

data class Status(
    val short: String
)