package org.themoviedb.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRetrofitNetwork {

    private const val API_V3_URL = "https://api.themoviedb.org/3/"

    fun makeRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_V3_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
