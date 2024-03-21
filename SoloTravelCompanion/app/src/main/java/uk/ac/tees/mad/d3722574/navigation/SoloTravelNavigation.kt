package uk.ac.tees.mad.d3722574.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3722574.ui.presentation.SplashDestination
import uk.ac.tees.mad.d3722574.ui.presentation.SplashScreen
import uk.ac.tees.mad.d3722574.ui.presentation.homescreen.HomeDestination
import uk.ac.tees.mad.d3722574.ui.presentation.homescreen.HomeScreen

@Composable
fun SoloTravelNavigation() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = SplashDestination.routeName) {
        composable(SplashDestination.routeName) {
            SplashScreen(
                onAnimationFinish = {
                    scope.launch(Dispatchers.Main) {
                        navController.popBackStack()
                        navController.navigate(HomeDestination.routeName)
                    }
                }
            )
        }
        composable(HomeDestination.routeName) {
            HomeScreen()
        }
    }
}

interface NavigationDestination {
    val routeName: String
}