package uk.ac.tees.mad.d3722574.ui.presentation.profilescreen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.d3722574.R
import uk.ac.tees.mad.d3722574.data.User
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.ui.presentation.loginscreen.LoginDestination
import uk.ac.tees.mad.d3722574.ui.presentation.travel.MyTravelDestination
import uk.ac.tees.mad.d3722574.util.Util.createImageFile
import uk.ac.tees.mad.d3722574.viewmodel.ProfileViewModel
import java.util.Objects


object ProfileDestination : NavigationDestination {
    override val routeName: String
        get() = "profile"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    val context = LocalContext.current


    var showLoader by remember {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf("")
    }

    var bio by remember {
        mutableStateOf("")
    }

    var intrest by remember {
        mutableStateOf("")
    }

    var profileUrl by remember {
        mutableStateOf("")
    }

    var profileImageGotChanged by remember {
        mutableStateOf(false)
    }

    val viewModel: ProfileViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getProfileDetails()
    }

    LaunchedEffect(viewModel.profileCallPutStatus.intValue) {
        showLoader =  viewModel.profileCallPutStatus.intValue == -1
    }


    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "uk.ac.tees.mad.d3722574.provider", file
    )


    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            profileImageGotChanged = true
            profileUrl = uri.toString()
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewModel.user.value) {

        viewModel.user.value?.let {
            name = it.name ?: ""
            bio = it.bio ?: ""
            intrest = it.intrest ?: ""
            profileUrl = it.profileUrl ?: ""
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
        Column(modifier = Modifier.fillMaxSize()) {

            TopAppBar(title = {
                Text(text = "Profile")
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
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {


                Image(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clickable {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                )
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape)
                        .width(160.dp)
                        .height(160.dp),
                    painter =
                    if (profileUrl.isEmpty()) painterResource(R.drawable.welcome)
                    else rememberAsyncImagePainter(model = profileUrl),
                    contentDescription = ""
                )


                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "Name",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    value = name,
                    onValueChange = {
                        name = it
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
                    text = "Bio",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(top = 2.dp),
                    value = bio,
                    onValueChange = {
                        bio = it
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
                    maxLines = 3,
                )

                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "Interests",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    value = intrest,
                    onValueChange = {
                        intrest = it
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

                Button(
                    onClick = {
                        if (name.isEmpty()) {
                            Toast.makeText(context, "Enter Username", Toast.LENGTH_SHORT).show()
                        } else if (bio.isEmpty()) {
                            Toast.makeText(context, "Enter Bio", Toast.LENGTH_SHORT).show()
                        } else if (intrest.isEmpty()) {
                            Toast.makeText(context, "Enter Interests", Toast.LENGTH_SHORT).show()
                        } else if (profileUrl.isEmpty()) {
                            Toast.makeText(context, "Select Profile Pic", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.putProfileDetails(
                                context,
                                User(name, bio, profileUrl, intrest),
                                profileImageGotChanged
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_blue))
                )
                {
                    Text(text = "Update", style = MaterialTheme.typography.bodyLarge)
                }


                Button(
                    onClick = {
                       navController.navigate(MyTravelDestination.routeName)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_blue))
                )
                {
                    Text(text = "My Travels", style = MaterialTheme.typography.bodyLarge)
                }

                Button(
                    onClick = {
                        viewModel.logout()
                        navController.navigate(LoginDestination.routeName)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_blue))
                )
                {
                    Text(text = "Logout", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

    }
}