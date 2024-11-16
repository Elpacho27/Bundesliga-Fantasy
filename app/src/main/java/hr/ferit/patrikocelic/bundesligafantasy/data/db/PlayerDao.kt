package hr.ferit.patrikocelic.bundesligafantasy.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerUiModel

@Dao
interface PlayerDao {

    @Transaction
    suspend fun replaceData(players: List<PlayerUiModel>) {
        deleteAll()
        insertAll(players)
    }

    @Query("DELETE FROM player")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(players: List<PlayerUiModel>)

    @Query("SELECT * FROM player WHERE position = :position ORDER BY lastname")
    fun getPlayersByPosition(position: String): LiveData<List<PlayerUiModel>>
}