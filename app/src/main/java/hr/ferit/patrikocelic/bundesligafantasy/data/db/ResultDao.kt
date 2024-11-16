package hr.ferit.patrikocelic.bundesligafantasy.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hr.ferit.patrikocelic.bundesligafantasy.data.models.Result

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(result: Result)

    @Query("SELECT * FROM result ORDER BY id DESC")
    fun observeAll(): LiveData<List<Result>>
}