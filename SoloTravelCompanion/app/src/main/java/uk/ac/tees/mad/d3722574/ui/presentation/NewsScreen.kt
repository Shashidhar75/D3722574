package uk.ac.tees.mad.d3722574.ui.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.d3722574.data.News
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.viewmodel.NewsViewModel


object NewsDestination : NavigationDestination {
    override val routeName: String
        get() = "news"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun NewsScreen(navController: NavController? = null) {


    val viewModel: NewsViewModel = hiltViewModel()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getNews(context)
    }


    if (viewModel.showLoader) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
        }
    } else {

        Column(modifier = Modifier.fillMaxSize()) {

            TopAppBar(title = {
                Text(text = "News")
            },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController?.popBackStack()
                            },
                        contentDescription = ""
                    )
                })

            LazyColumn {
                items(viewModel.newsList?.results?.size ?: 0) { index ->
                    NewsItem(news = viewModel.newsList?.results?.get(index)!!, context = context)
                }
            }

        }
    }

}

@Composable
fun NewsItem(news: News,context:Context) {

    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp),
                painter = rememberAsyncImagePainter(model = news.image_url),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = news.title?:"",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 17.sp
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = news.description?:"",
                fontSize = 18.sp,
                lineHeight = 17.sp
            )
            Button(
                modifier = Modifier.padding(top = 12.dp),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse(news.link))
                    context.startActivity(intent)
            }) {
                Text("View full article")
            }
        }
    }

}