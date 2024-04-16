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
import uk.ac.tees.mad.d3722574.ui.presentation.loginscreen.LoginDestination
import uk.ac.tees.mad.d3722574.ui.presentation.loginscreen.LoginScreen
import uk.ac.tees.mad.d3722574.ui.presentation.signupscreen.SignupDestination
import uk.ac.tees.mad.d3722574.ui.presentation.signupscreen.SignupScren
import uk.ac.tees.mad.d3722574.ui.presentation.welcomescreen.WelcomeDestination
import uk.ac.tees.mad.d3722574.ui.presentation.welcomescreen.WelcomeScreen

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
                        navController.navigate(WelcomeDestination.routeName)
                    }
                }
            )
        }
        composable(LoginDestination.routeName){
            LoginScreen(onNavigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(HomeDestination.routeName)
            }, onNavigateToRegistration = {
                navController.navigate(SignupDestination.routeName)
            })
        }
        composable(SignupDestination.routeName){
            SignupScren(onHomeScreen = {
                navController.popBackStack()
                navController.navigate(HomeDestination.routeName)

            }, onNavigateToLogin = {
                navController.navigate(LoginDestination.routeName)

            })
        }

        composable(WelcomeDestination.routeName){
            WelcomeScreen(onLoginScreen = {
                navController.navigate(LoginDestination.routeName)
            }, onNavigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(HomeDestination.routeName)
            }, onSignUpScreen = {
                navController.navigate(SignupDestination.routeName)

            }
            )
        }
        composable(HomeDestination.routeName) {
            HomeScreen(onNavigateToWelcomeScren = {
                navController.popBackStack()
                navController.navigate(WelcomeDestination.routeName)
            })
        }
    }
}

interface NavigationDestination {
    val routeName: String
}