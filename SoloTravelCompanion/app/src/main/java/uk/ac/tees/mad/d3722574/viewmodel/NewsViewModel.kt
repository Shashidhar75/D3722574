package uk.ac.tees.mad.d3722574.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.ac.tees.mad.d3722574.data.NewsList
import uk.ac.tees.mad.d3722574.repository.ApiClient
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {


    var newsList by mutableStateOf<NewsList?>(null)

    var showLoader by mutableStateOf(false)

    fun getNews(context: Context) {
        showLoader = true
        val call = ApiClient.apiService.getTravelNews(title = "travel", country = "gb")
        call.enqueue(object: Callback<NewsList> {

            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if(response.isSuccessful) {
                    showLoader = false
                    newsList = response.body()
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
               showLoader = false
                Toast.makeText(context,"Failed to Fetch news",Toast.LENGTH_SHORT).show()
            }

        })
    }
}
