package uk.ac.tees.mad.d3722574.ui.presentation.travel

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import uk.ac.tees.mad.d3722574.R
import uk.ac.tees.mad.d3722574.data.TravelDetails
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.util.Util
import uk.ac.tees.mad.d3722574.viewmodel.MyTravelViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID


object AddTravelDestination : NavigationDestination {
    override val routeName: String
        get() = "addtravel"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun AddNewTravel(navController: NavController? = null) {

    val viewModel:MyTravelViewModel = hiltViewModel()

    val context = LocalContext.current


    var placeName by remember {
        mutableStateOf("")
    }


    var location by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    var showGoogleMap by remember {
        mutableStateOf(false)
    }

    var locationToDisplay by remember {
        mutableStateOf("")
    }


    var showLoader by remember {
        mutableStateOf(false)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 15f)
    }

    LaunchedEffect(location) {
        locationToDisplay = Util.getGeoLocation(context, location) ?: ""
    }

    var fusedLocationClient by remember {
        mutableStateOf<FusedLocationProviderClient?>(null)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient?.lastLocation?.addOnSuccessListener { loc: Location? ->
                location = LatLng(loc?.latitude ?: 0.0, loc?.longitude ?: 0.0)
            }
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val datePickerState = rememberDatePickerState(
        selectableDates = object :SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= System.currentTimeMillis()
            }
        }
    )

    LaunchedEffect(Unit) {
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient?.lastLocation?.addOnSuccessListener { loc: Location? ->
                location = LatLng(loc?.latitude ?: 0.0, loc?.longitude ?: 0.0)
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    var date  =  datePickerState.selectedDateMillis?.let {
        SimpleDateFormat("dd/MM/yyyy").format(Date(it))
    } ?:""

    LaunchedEffect(viewModel.profileCallGetStatus.intValue, viewModel.addTravelCallStatus.intValue) {
        showLoader = viewModel.profileCallGetStatus.intValue == -1 || viewModel.addTravelCallStatus.intValue == -1
    }

    LaunchedEffect(Unit) {
        viewModel.getProfileDetails()
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


        if (showGoogleMap) {

            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    onMapClick = {
                        showGoogleMap = false
                        location = it
                    },
                    properties = MapProperties(isMyLocationEnabled = true),
                    uiSettings = MapUiSettings(
                        compassEnabled = true
                    ),
                    cameraPositionState = cameraPositionState
                )
            }

        } else if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    Button(onClick = {
                        showDatePicker = false
                    }) {

                        Text(text = "Ok")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        } else {


            Column(modifier = Modifier.fillMaxSize()) {

                TopAppBar(title = {
                    Text(text = "New Travel")
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

                Column(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = "Place Name",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        value = placeName,
                        onValueChange = {
                            placeName = it
                        },
                        placeholder = {
                            Text(text = "Enter Place Name")
                        },
                        shape = RoundedCornerShape(10.dp),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF0084FE),
                            unfocusedBorderColor = Color(0xFF0084FE),
                            focusedLabelColor = Color(0xFF0084FE),
                            errorSupportingTextColor = Color(0xFFC65B52),
                            errorBorderColor = Color(0xFFC65B52),
                            errorSuffixColor = Color(0xFFC65B52),
                            errorLabelColor = Color(0xFFC65B52),
                            errorLeadingIconColor = Color(0xFFC65B52),
                            errorTextColor = Color(0xFFC65B52),


                            ),
                        maxLines = 1,
                    )



                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = "Place Location",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        readOnly = true,
                        value = locationToDisplay,
                        onValueChange = {},
                        placeholder = {
                            Text(text = "Select Location")
                        },
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.clickable {
                                    val permissionCheck = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    )
                                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                                        showGoogleMap = true
                                    } else {
                                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                    }
                                },
                                painter = painterResource(id = R.drawable.baseline_location_on_24),
                                contentDescription = ""
                            )
                        },

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF0084FE),
                            unfocusedBorderColor = Color(0xFF0084FE),
                            focusedLabelColor = Color(0xFF0084FE),
                            errorSupportingTextColor = Color(0xFFC65B52),
                            errorBorderColor = Color(0xFFC65B52),
                            errorSuffixColor = Color(0xFFC65B52),
                            errorLabelColor = Color(0xFFC65B52),
                            errorLeadingIconColor = Color(0xFFC65B52),
                            errorTextColor = Color(0xFFC65B52),


                            ),
                        maxLines = 1,
                    )


                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = "Date",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        readOnly = true,
                        value = date,

                        onValueChange = {
                            date = it
                        },
                        placeholder = {
                            Text(text = "Select Date")
                        },
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.clickable {
                                    showDatePicker = true
                                },
                                painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                                contentDescription = ""
                            )
                        },

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF0084FE),
                            unfocusedBorderColor = Color(0xFF0084FE),
                            focusedLabelColor = Color(0xFF0084FE),
                            errorSupportingTextColor = Color(0xFFC65B52),
                            errorBorderColor = Color(0xFFC65B52),
                            errorSuffixColor = Color(0xFFC65B52),
                            errorLabelColor = Color(0xFFC65B52),
                            errorLeadingIconColor = Color(0xFFC65B52),
                            errorTextColor = Color(0xFFC65B52),


                            ),
                        maxLines = 1,
                    )


                    Button(
                        onClick = {
                            if (placeName.isEmpty()) {
                                Toast.makeText(context, "Enter Place name", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (locationToDisplay.isEmpty()) {
                                Toast.makeText(context, "Select Location", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (date.isEmpty()) {
                                Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show()
                            } else {

                                viewModel.addNewTravel(
                                    TravelDetails(
                                        UUID.randomUUID().toString(),
                                        FirebaseAuth.getInstance().currentUser?.uid?:"",
                                        travellerName =  viewModel.user.value?.name,
                                        travelLocation = locationToDisplay,
                                        travelLat = location.latitude,
                                        travelLong = location.longitude,
                                        travelDate = date,
                                        travellerProfileUrl = viewModel.user.value?.profileUrl
                                    ),
                                    context
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_blue))
                    )
                    {
                        Text(text = "Add", style = MaterialTheme.typography.bodyLarge)
                    }


                }
            }
        }
    }
}
