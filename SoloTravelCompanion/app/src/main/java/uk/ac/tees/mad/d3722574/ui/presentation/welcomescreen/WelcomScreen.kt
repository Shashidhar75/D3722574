package uk.ac.tees.mad.d3722574.ui.presentation.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3722574.R
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.repository.GlobalConstants


object WelcomeDestination : NavigationDestination {
    override val routeName: String
        get() = "welcome"
}

@Composable
fun WelcomeScreen(
    onLoginScreen: () -> Unit,
    onSignUpScreen : () -> Unit,
    onNavigateToHomeScreen: () -> Unit
) {
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }

    if(user!=null){
        onNavigateToHomeScreen()
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(12.dp),
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.welcome),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                )

                Spacer(modifier = Modifier.size(30.dp))

                Text(
                    text = "Welcome to the app",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "We're excited to help you book and manage ",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .padding(horizontal = 20.dp),
                    color = Color.Gray
                )
                Text(
                    text = "your service appointments with ease.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(horizontal = 44.dp, vertical = 2.dp),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.size(32.dp))

                Button(
                    onClick = { onLoginScreen() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 44.dp, vertical = 2.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF0084FE))
                ) {
                    Text(text = "Login", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.size(32.dp))

                Text(
                    text = "Create an account",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        onSignUpScreen()
                    }
                )
            }

        }
    }
}


@Preview
@Composable
private fun View() {
    WelcomeScreen({},{},{})
}