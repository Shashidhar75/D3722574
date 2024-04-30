package uk.ac.tees.mad.d3722574.ui.presentation.homescreen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.d3722574.data.Destination
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.viewmodel.HomeViewModel


object DestinationDestination : NavigationDestination {
    override val routeName: String
        get() = "destination"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationScreen(id: String, viewModel: HomeViewModel, navController: NavController) {


    val context = LocalContext.current

    var selectedDestination by remember {
        mutableStateOf<Destination?>(null)
    }

    LaunchedEffect(Unit) {
        selectedDestination = viewModel.destinationList.value.first {
            it.id == id
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(title = {
            Text(text = "Destination Details")
        },
            navigationIcon = {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    contentDescription = ""
                )
            })

        Column(
            Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Image(

                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                painter = rememberAsyncImagePainter(selectedDestination?.url),
                contentDescription = ""
            )

            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
                text = selectedDestination?.name ?: "",
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
                text = selectedDestination?.description ?: "",
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
                text = selectedDestination?.place ?: "",
                fontSize = 14.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
                text = "Entry Fee : ${selectedDestination?.entryFee}",
                fontSize = 14.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
                text = "${(selectedDestination?.rating ?: "")} ★",
                fontSize = 14.sp
            )

            Button(onClick = {
                val url =
                    "https://maps.google.com/maps?q=${selectedDestination?.latitude ?: 0.0},${selectedDestination?.longitude ?: 0.0}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.setPackage("com.google.android.apps.maps")
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            }) {
                Text(text = "Open Location")
            }
        }
    }
}