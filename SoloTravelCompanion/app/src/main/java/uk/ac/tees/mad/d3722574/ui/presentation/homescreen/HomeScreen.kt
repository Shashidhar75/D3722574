package uk.ac.tees.mad.d3722574.ui.presentation.homescreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination

object HomeDestination: NavigationDestination{
    override val routeName: String
        get() = "home"
}

@Composable
fun HomeScreen() {
    Text(text = "Home")
}