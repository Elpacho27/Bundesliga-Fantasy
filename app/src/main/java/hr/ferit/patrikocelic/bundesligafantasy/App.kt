package hr.ferit.patrikocelic.bundesligafantasy

import android.app.Application

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}