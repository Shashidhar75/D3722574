package uk.ac.tees.mad.d3722574.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3722574.data.Category
import uk.ac.tees.mad.d3722574.data.Destination
import uk.ac.tees.mad.d3722574.data.TravelDetails
import uk.ac.tees.mad.d3722574.data.User
import uk.ac.tees.mad.d3722574.repository.DataRepository
import uk.ac.tees.mad.d3722574.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var destinationCallStatus = mutableIntStateOf(-1)
    var destinationList = mutableStateOf(listOf<Destination>())
    var travellerCallStatus = mutableIntStateOf(-1)
    var travellerList = mutableStateOf(listOf<TravelDetails>())
    var user = mutableStateOf<User?>(User())
    var categories = mutableStateOf(
        listOf(
            Category("All","https://t3.ftcdn.net/jpg/06/06/79/70/360_F_606797008_rGPPk6bFWDQydnX7g7w1w9dVVZ4mD22J.jpg"),
            Category("Coastal", "https://about-britain.com/photos/dorset-coast.jpg"),
            Category(
                "Mountains",
                "https://where2walk.co.uk/wp-content/uploads/2014/03/Place-Fell.jpg"
            ),
            Category(
                "Cave",
                "https://files.holidaycottages.co.uk/blogs%2F1628603736115-1628603736115.png"
            ),
            Category(
                "Lake",
                "https://static.holidu.com/wp-content/uploads/Buttermere-credit-Michael-Walsh-CC-BY-https-creativecommons.orglicensesby3.0.jpg"
            ),
            Category(
                "island",
                "https://media.cntraveller.com/photos/611be8f06ab96bf3ecf1edef/16:9/w_1600%2Cc_limit/gateholm-island-pembrokeshire-shutterstock_1182091540.jpg"
            )

        )
    )

    fun getAllTravellers() {

        viewModelScope.launch {

            DataRepository.getAllTravelDetails(
                onSuccess = { allTravels ->
                    travellerCallStatus.intValue = 1
                    travellerList.value = allTravels
                },
                onFailure = {
                    travellerCallStatus.intValue = 0
                },
                FirebaseAuth.getInstance().currentUser?.uid ?: "",
            )

        }
    }

    fun getUser() {
        viewModelScope.launch {
            ProfileRepository.getProfileDetails(onSuccess = {
                user.value = it
            },{
                user.value = null
            })
        }
    }


    fun getAllDestination() {
        viewModelScope.launch {
            DataRepository.getAllDestinations(
                onSuccess = {
                    destinationCallStatus.intValue = 1
                    destinationList.value = it
                },
                onFailure = {
                    destinationCallStatus.intValue = 0
                }
            )
        }
    }

    fun getCategories() {
        val newList = mutableListOf<Category>()
        newList.add(Category("Coastal", "https://about-britain.com/photos/dorset-coast.jpg"))
        newList.add(
            Category(
                "Mountains",
                "https://where2walk.co.uk/wp-content/uploads/2014/03/Place-Fell.jpg"
            )
        )
        newList.add(
            Category(
                "Cave",
                "https://files.holidaycottages.co.uk/blogs%2F1628603736115-1628603736115.png"
            )
        )
        newList.add(
            Category(
                "Lave",
                "https://static.holidu.com/wp-content/uploads/Buttermere-credit-Michael-Walsh-CC-BY-https-creativecommons.orglicensesby3.0.jpg"
            )
        )
        newList.add(
            Category(
                "island",
                "https://media.cntraveller.com/photos/611be8f06ab96bf3ecf1edef/16:9/w_1600%2Cc_limit/gateholm-island-pembrokeshire-shutterstock_1182091540.jpg"
            )
        )
        categories.value = newList
    }

}