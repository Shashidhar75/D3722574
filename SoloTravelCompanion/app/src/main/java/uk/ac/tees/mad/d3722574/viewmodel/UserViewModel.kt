package uk.ac.tees.mad.d3722574.viewmodel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3722574.repository.AuthRepository
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor() :ViewModel() {



    private var repository = AuthRepository()

    var loginStatus = mutableStateOf(false)
    var registerStatus = mutableStateOf(false)



    fun signUpUser(context: Context,email:String,password:String,confirmPassword:String) = viewModelScope.launch {


        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context,"Enter Valid Email Id",Toast.LENGTH_SHORT).show()
        } else if(password.isEmpty() || password.length < 6 ) {
            Toast.makeText(context,"Password should be of length 6",Toast.LENGTH_SHORT).show()
        } else  if(password != confirmPassword ) {
            Toast.makeText(context,"Password and confirm password should be same",Toast.LENGTH_SHORT).show()
        } else{
            repository.createUser(
                email,
                password
            ) { isSuccessful ->
                if (isSuccessful) {
                    registerStatus.value = true
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    registerStatus.value = false
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }



    fun loginUser(context: Context,email: String,password: String) = viewModelScope.launch {


           if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
               Toast.makeText(context,"Enter Valid Email Id",Toast.LENGTH_SHORT).show()
           } else if(password.isEmpty() || password.length < 6 ) {
               Toast.makeText(context,"Password should be of length 6",Toast.LENGTH_SHORT).show()
           } else {

               repository.login(
                   email,
                   password
               ) { isSuccessful ->
                   if (isSuccessful) {
                       Toast.makeText(
                           context,
                           "success Login",
                           Toast.LENGTH_SHORT
                       ).show()
                       loginStatus.value = true

                   } else {
                       loginStatus.value = false
                       Toast.makeText(
                           context,
                           "Failed Login",
                           Toast.LENGTH_SHORT
                       ).show()
                   }

               }
           }
    }

}