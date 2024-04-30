package uk.ac.tees.mad.d3722574.ui.presentation.homescreen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.d3722574.R
import uk.ac.tees.mad.d3722574.data.Destination
import uk.ac.tees.mad.d3722574.data.TravelDetails
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.ui.presentation.profilescreen.ProfileDestination
import uk.ac.tees.mad.d3722574.ui.presentation.travel.AddTravelDestination
import uk.ac.tees.mad.d3722574.viewmodel.HomeViewModel

object HomeDestination : NavigationDestination {
    override val routeName: String
        get() = "home"
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {

    val context = LocalContext.current


    var showLoader by remember {
        mutableStateOf(false)
    }


    var destinationToShow by remember {
        mutableStateOf(viewModel.destinationList.value)
    }


    val categories = viewModel.categories

    var selectedCategory by remember {
        mutableStateOf("All")
    }


    val travellersPagerState = rememberPagerState() {
        viewModel.travellerList.value.size
    }

    val categoryPagerState = rememberPagerState() {
        categories.value.size
    }

    val placesPagerState = rememberPagerState() {
        destinationToShow.size
    }




    LaunchedEffect(viewModel.destinationCallStatus.intValue,viewModel.travellerCallStatus.intValue) {

        showLoader = viewModel.destinationCallStatus.intValue == -1 || viewModel.travellerCallStatus.intValue == -1

    }

    LaunchedEffect(selectedCategory, viewModel.destinationList.value) {
        destinationToShow = viewModel.destinationList.value.toList()
        destinationToShow = if (selectedCategory == "All") {
            viewModel.destinationList.value
        } else {
            destinationToShow.filter {
                it.type?.contains(selectedCategory, ignoreCase = true) ?: false
            }
        }
    }


    LaunchedEffect(Unit) {

        viewModel.getAllDestination()
        viewModel.getAllTravellers()
        viewModel.getUser()

    }


    Scaffold(
        floatingActionButton = {
            Button(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .clip(CircleShape),
                onClick = {
                    if(viewModel.user.value?.name ==null || viewModel.user.value?.profileUrl == null) {
                        Toast.makeText(context,"Add Profile Details Before Adding Your Travel",Toast.LENGTH_SHORT).show()
                        navController.navigate(ProfileDestination.routeName)
                    } else {
                        navController.navigate(AddTravelDestination.routeName)
                    }

            }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Add,
                        modifier = Modifier.padding(vertical = 10.dp),
                        contentDescription =""
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = "Add Travel")
                }
            
            }

        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Solo Travel"
                    )
                },

                actions = {
                    Icon(
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                navController.navigate(ProfileDestination.routeName)
                            },
                        painter = painterResource(id = R.drawable.baseline_account_circle_24),
                        contentDescription = ""
                    )
                },

                navigationIcon = {
                    Icon(
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.padding(start = 8.dp),
                        painter = painterResource(id = R.drawable.baseline_account_circle_24),
                        contentDescription = ""
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.primary_blue),
                    titleContentColor = colorResource(id = R.color.white)
                )

            )
        }
    ) { paddingValues ->

        if (showLoader) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.size(30.dp))
            }
        } else {


            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                    fontSize = 22.sp,
                    text = "Travellers Around Me"
                )
                
                if(viewModel.travellerList.value.isNotEmpty()) {

                    HorizontalPager(
                        pageSize = PageSize.Fixed(200.dp),
                        state = travellersPagerState
                    ) {
                        TravellerCard(viewModel.travellerList.value[it])
                    }
                } else {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 8.dp),
                        textAlign = TextAlign.Center,
                        text = "No Travellers Around You"
                    )
                }


                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 8.dp),
                    fontSize = 22.sp,
                    text = "Categories"
                )


                HorizontalPager(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.padding(top = 16.dp),
                    pageSize = PageSize.Fixed(100.dp),
                    state = categoryPagerState
                ) {
                    Column(
                        modifier = Modifier
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.clickable {
                                selectedCategory = categories.value[it].name ?: "All"
                            },
                            shape = RoundedCornerShape(32.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(60.dp),
                                painter = rememberAsyncImagePainter(categories.value[it].image),
                                contentDescription = ""
                            )
                        }
                        Text(
                            fontWeight = if (categories.value[it].name == selectedCategory) FontWeight.Bold else FontWeight.Normal,
                            text = categories.value[it].name ?: ""
                        )
                    }
                }

                HorizontalPager(
                    contentPadding = PaddingValues(8.dp),
                    pageSpacing = 8.dp,
                    pageSize = PageSize.Fixed(150.dp),
                    state = placesPagerState
                ) {
                    DestinationsItem(destinationToShow[it], onClick = {
                        navController.navigate(DestinationDestination.routeName + "/${destinationToShow[it].id}")
                    })
                }
            }
        }

    }
}


@Composable
private fun TravellerCard(travelDetails: TravelDetails) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Card(shape = RoundedCornerShape(8.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .height(150.dp)
                        .width(150.dp),
                    painter = rememberAsyncImagePainter(travelDetails.travellerProfileUrl),
                    contentDescription = ""
                )
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
                        text = travelDetails.travelDate ?:"",
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
                        text = travelDetails.travelLocation ?:"",
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                    )
                }
            }

        }
    }

}


@Composable
fun DestinationsItem(destination: Destination, onClick: () -> Unit) {

    Card(shape = RoundedCornerShape(8.dp),
        modifier = Modifier.clickable {
            onClick.invoke()
        }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(8.dp))
                    .height(150.dp)
                    .width(150.dp),
                contentScale = ContentScale.FillBounds,
                painter = rememberAsyncImagePainter(destination.url),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.padding(top = 8.dp, start = 4.dp),
                text = destination.name ?: "",
                fontSize = 12.sp,
                lineHeight = 12.sp,
                textAlign = TextAlign.Center
            )


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
                    text = destination.place ?: "",
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            }

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "${(destination.rating ?: "")} ★",
                    fontSize = 14.sp
                )
            }

        }
    }

}

