package uk.ac.tees.mad.d3722574.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3722574.ui.presentation.NewsDestination
import uk.ac.tees.mad.d3722574.ui.presentation.NewsScreen
import uk.ac.tees.mad.d3722574.ui.presentation.homescreen.DestinationDestination
import uk.ac.tees.mad.d3722574.ui.presentation.homescreen.DestinationScreen
import uk.ac.tees.mad.d3722574.ui.presentation.homescreen.HomeDestination
import uk.ac.tees.mad.d3722574.ui.presentation.homescreen.HomeScreen
import uk.ac.tees.mad.d3722574.ui.presentation.loginscreen.LoginDestination
import uk.ac.tees.mad.d3722574.ui.presentation.loginscreen.LoginScreen
import uk.ac.tees.mad.d3722574.ui.presentation.profilescreen.ProfileDestination
import uk.ac.tees.mad.d3722574.ui.presentation.profilescreen.ProfileScreen
import uk.ac.tees.mad.d3722574.ui.presentation.signupscreen.SignupDestination
import uk.ac.tees.mad.d3722574.ui.presentation.signupscreen.SignupScreen
import uk.ac.tees.mad.d3722574.ui.presentation.splashscreen.SplashDestination
import uk.ac.tees.mad.d3722574.ui.presentation.splashscreen.SplashScreen
import uk.ac.tees.mad.d3722574.ui.presentation.travel.AddNewTravel
import uk.ac.tees.mad.d3722574.ui.presentation.travel.AddTravelDestination
import uk.ac.tees.mad.d3722574.ui.presentation.travel.MyTravelDestination
import uk.ac.tees.mad.d3722574.ui.presentation.travel.MyTravels
import uk.ac.tees.mad.d3722574.ui.presentation.welcomescreen.WelcomeDestination
import uk.ac.tees.mad.d3722574.ui.presentation.welcomescreen.WelcomeScreen
import uk.ac.tees.mad.d3722574.viewmodel.HomeViewModel

@Composable
fun SoloTravelNavigation() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val viewModel: HomeViewModel = viewModel()


    NavHost(navController = navController, startDestination = SplashDestination.routeName) {
        composable(SplashDestination.routeName) {
            SplashScreen(
                onAnimationFinish = {
                    scope.launch(Dispatchers.Main) {
                        navController.navigate(WelcomeDestination.routeName) {
                            popUpTo(SplashDestination.routeName) {
                                inclusive = true
                            }
                        }
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
            SignupScreen(onHomeScreen = {
                navController.navigate(HomeDestination.routeName) {
                    popUpTo(SignupDestination.routeName) {
                        inclusive = true
                    }
                }

            }, onNavigateToLogin = {
                navController.popBackStack()

            })
        }

        composable(DestinationDestination.routeName + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ){
            DestinationScreen(it.arguments?.getString("id")?:"",viewModel,navController)
        }

        composable(WelcomeDestination.routeName){
            WelcomeScreen(onLoginScreen = {
                navController.navigate(LoginDestination.routeName)
            }, onNavigateToHomeScreen = {


                navController.navigate(HomeDestination.routeName) {
                    popUpTo(WelcomeDestination.routeName) {
                        inclusive = true
                    }
                }
            }, onSignUpScreen = {
                navController.navigate(SignupDestination.routeName)

            }
            )
        }
        composable(HomeDestination.routeName) {
            HomeScreen(viewModel,navController)
        }

        composable(ProfileDestination.routeName) {
            ProfileScreen(navController)
        }

        composable(AddTravelDestination.routeName) {
            AddNewTravel(navController)
        }

        composable(MyTravelDestination.routeName) {
            MyTravels(navController)
        }

        composable(NewsDestination.routeName) {
            NewsScreen(navController)
        }
    }
}

interface NavigationDestination {
    val routeName: String
}