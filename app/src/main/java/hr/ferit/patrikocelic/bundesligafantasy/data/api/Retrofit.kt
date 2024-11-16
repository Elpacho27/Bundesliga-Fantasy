package hr.ferit.patrikocelic.bundesligafantasy.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Retrofit {

    val instance: RapidApi =
        Retrofit.Builder()
            .baseUrl(RapidApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
}