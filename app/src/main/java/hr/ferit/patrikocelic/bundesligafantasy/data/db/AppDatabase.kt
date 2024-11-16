package hr.ferit.patrikocelic.bundesligafantasy.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.ferit.patrikocelic.bundesligafantasy.App
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerUiModel
import hr.ferit.patrikocelic.bundesligafantasy.data.models.Result

@Database(
    entities = [
        PlayerUiModel::class, Result::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPlayerDao(): PlayerDao
    abstract fun getResultDao(): ResultDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        App.INSTANCE,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                }
            }

            return instance!!
        }
    }
}