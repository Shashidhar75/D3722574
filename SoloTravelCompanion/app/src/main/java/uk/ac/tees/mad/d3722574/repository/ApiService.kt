package uk.ac.tees.mad.d3722574.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.d3722574.data.NewsList

interface ApiService {
    @GET("api/1/news?apikey=pub_42559802af3696b23614894e7cbdc066522ae")
    fun getTravelNews(
        @Query("qInTitle") title: String,
        @Query("country") country: String,
    ): Call<NewsList>
}