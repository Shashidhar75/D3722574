package uk.ac.tees.mad.d3722574.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3722574.data.TravelDetails
import uk.ac.tees.mad.d3722574.data.User
import uk.ac.tees.mad.d3722574.repository.DataRepository
import uk.ac.tees.mad.d3722574.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class MyTravelViewModel @Inject constructor() : ViewModel() {


    var profileCallGetStatus = mutableIntStateOf(-1)
    var user = mutableStateOf<User?>(User())

    var addTravelCallStatus = mutableIntStateOf(-2)

    var myTravelsStatus = mutableIntStateOf(-1)
    var myTravels = mutableStateOf(listOf<TravelDetails>())

    var deleteCallStatus = mutableIntStateOf(-2)






    fun getProfileDetails() {
        viewModelScope.launch {
            ProfileRepository.getProfileDetails(
                onSuccess = { userRes ->
                    user.value = userRes
                    profileCallGetStatus.intValue = 1
                },
                onFailure = {
                    user.value = null
                    profileCallGetStatus.intValue = 0
                }
            )
        }
    }

    fun getMyTravels() {

        viewModelScope.launch {

            DataRepository.getMyTravelDetails(
                onSuccess = { allTravels ->
                    myTravelsStatus.intValue = 1
                    myTravels.value = allTravels
                },
                onFailure = {
                    myTravelsStatus.intValue = 0
                },
                FirebaseAuth.getInstance().currentUser?.uid ?: "",
            )

        }
    }

    fun deleteMyTravel(travelId:String,context: Context) {
        deleteCallStatus.intValue  = -1
        viewModelScope.launch {
            DataRepository.deleteMyTravel(travelId,{
                deleteCallStatus.intValue  = 1
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show()
            }, {
                deleteCallStatus.intValue  = 0
                Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show()
            })
        }

    }



    fun addNewTravel(travelDetails: TravelDetails,context: Context) {
        addTravelCallStatus.intValue = -1
        viewModelScope.launch {
            DataRepository.saveNewTravel(
                travelDetails = travelDetails,
                onSuccess = {
                    Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show()
                    addTravelCallStatus.intValue = 1
                },
                onFailure = {
                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                    addTravelCallStatus.intValue = 0
                }
            )
        }
    }

}