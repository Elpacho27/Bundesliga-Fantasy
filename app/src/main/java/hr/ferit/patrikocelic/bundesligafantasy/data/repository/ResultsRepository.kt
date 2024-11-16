package hr.ferit.patrikocelic.bundesligafantasy.data.repository

import hr.ferit.patrikocelic.bundesligafantasy.data.db.AppDatabase
import hr.ferit.patrikocelic.bundesligafantasy.data.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ResultsRepository {

    suspend fun saveResult(result: Result) = withContext(Dispatchers.IO) {
        AppDatabase.getInstance().getResultDao().insert(result)
    }

    fun observeResults() = AppDatabase.getInstance().getResultDao().observeAll()
}