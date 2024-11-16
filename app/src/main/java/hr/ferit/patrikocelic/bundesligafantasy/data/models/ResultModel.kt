package hr.ferit.patrikocelic.bundesligafantasy.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result")
data class Result(
    val timestamp: Long,
    val value: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)