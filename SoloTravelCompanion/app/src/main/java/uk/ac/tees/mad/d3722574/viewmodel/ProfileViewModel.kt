package uk.ac.tees.mad.d3722574.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3722574.data.User
import uk.ac.tees.mad.d3722574.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    var profileCallGetStatus = mutableIntStateOf(-1)
    var profileCallPutStatus = mutableIntStateOf(-2)
    var user = mutableStateOf<User?>(User())


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

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun putProfileDetails(context: Context, user: User, profileImageGotChanged: Boolean) {
        profileCallPutStatus.intValue = -1
        viewModelScope.launch {
            ProfileRepository.putProfileDetails(
                context,
                user,
                profileImageGotChanged,
                onFailure = {
                    profileCallPutStatus.intValue = 0
                },
                onSuccess = {
                    profileCallPutStatus.intValue = 1
                }
            )
        }
    }


}