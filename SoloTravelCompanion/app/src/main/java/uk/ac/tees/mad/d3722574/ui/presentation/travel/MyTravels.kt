package uk.ac.tees.mad.d3722574.ui.presentation.travel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.d3722574.R
import uk.ac.tees.mad.d3722574.data.TravelDetails
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.viewmodel.MyTravelViewModel


object MyTravelDestination : NavigationDestination {
    override val routeName: String
        get() = "mytravel"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun MyTravels(navController: NavController? = null) {


    val viewModel: MyTravelViewModel = hiltViewModel()


    val context = LocalContext.current
    var showLoader by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit) {
        viewModel.getMyTravels()
    }

    LaunchedEffect(viewModel.myTravelsStatus.intValue, viewModel.deleteCallStatus.intValue) {
        showLoader =
            viewModel.myTravelsStatus.intValue == -1 || viewModel.deleteCallStatus.intValue == -1
    }

    LaunchedEffect(viewModel.deleteCallStatus.intValue) {
        if (viewModel.deleteCallStatus.intValue == 1) {
            viewModel.getMyTravels()
        }
    }



    if (showLoader) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
        }
    } else {

        Column {
            TopAppBar(title = {
                Text(text = "My Travels")
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

            if (viewModel.myTravels.value.isNotEmpty()) {
                LazyColumn {
                    items(viewModel.myTravels.value.size) {
                        SingleItem(viewModel.myTravels.value[it]) { id ->
                            viewModel.deleteMyTravel(id, context)
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No Items Found")
                }
            }


        }


    }


}

@Composable

fun SingleItem(travelDetails: TravelDetails, onDelete: (id: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Card(shape = RoundedCornerShape(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {


                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 4.dp),
                        text = travelDetails.travellerName ?: "",
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        textAlign = TextAlign.Center
                    )

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier.padding(start = 2.dp),
                            text = travelDetails.travelDate ?: "",
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                        )

                    }
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location_on_24),
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier.padding(start = 2.dp),
                            text = travelDetails.travelLocation ?: "",
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                        )
                    }
                }

                Icon(
                    Icons.Default.Delete,
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .clickable {
                            onDelete.invoke(travelDetails.travelId ?: "")
                        },
                    contentDescription = ""
                )
            }

        }
    }
}