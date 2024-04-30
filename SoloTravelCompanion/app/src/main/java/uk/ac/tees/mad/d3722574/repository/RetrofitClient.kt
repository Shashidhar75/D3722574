package uk.ac.tees.mad.d3722574.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.newsdata.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}