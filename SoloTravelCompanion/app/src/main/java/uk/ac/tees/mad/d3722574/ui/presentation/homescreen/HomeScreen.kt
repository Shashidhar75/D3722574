package uk.ac.tees.mad.d3722574.ui.presentation.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.repository.GlobalConstants

object HomeDestination: NavigationDestination{
    override val routeName: String
        get() = "home"
}

@Composable
fun HomeScreen(
    onNavigateToWelcomeScren: () -> Unit
) {
Box {
    Button(onClick = {
        Firebase.auth.signOut()
        GlobalConstants.user = null
        onNavigateToWelcomeScren()
    }) {
        Text(text = "Home")
    }
}}