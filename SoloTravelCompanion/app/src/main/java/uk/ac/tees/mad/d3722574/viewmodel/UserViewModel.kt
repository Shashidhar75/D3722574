package uk.ac.tees.mad.d3722574.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.d3722574.repository.AuthRepository
import uk.ac.tees.mad.d3722574.repository.LoginState
import uk.ac.tees.mad.d3722574.repository.RegistrationState
import uk.ac.tees.mad.d3722574.repository.Screen
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() :ViewModel() {

    var registrationState = MutableStateFlow(RegistrationState())
        private set
    var loginState = MutableStateFlow(LoginState())
        private set

    var repository = AuthRepository()

    fun checkUser(user: FirebaseUser? = null,screen:Screen){
        if(user != null){
            if(screen==Screen.LOGIN_SCREEN)
                loginState.value.isLoginSuccessful = true
            else if(screen == Screen.SIGNUP_SCREEN)
                registrationState.value.isRegistrationSuccessful = true

        }
        return
    }

    //Google Token
    private var token :String = "829815412215-4u34mponcbpk9amrsmtrkho5b7cq5pbe.apps.googleusercontent.com"
    fun signUpUser(context: Context,email:String?,password:String?) = viewModelScope.launch {
        try {

            if (email=="" || password == "") {
                throw IllegalArgumentException("email and password can not be empty")
            }

            repository.createUser(
                email!!,
                password!!
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    registrationState.value.isRegistrationSuccessful =true
                } else {
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    registrationState.value.isRegistrationSuccessful =false
                }

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    fun signInWithGoogle(context: Context,launcher: ManagedActivityResultLauncher<Intent, ActivityResult>){
        val gso =
            GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail()
                .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        launcher.launch(googleSignInClient.signInIntent)
    }

    fun loginUser(context: Context,email: String?,password: String?) = viewModelScope.launch {
        try {

            if (email=="" || password == "") {
                throw IllegalArgumentException("email and password can not be empty")
            }

            repository.login(
                email!!,
                password!!
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginState.value.isLoginSuccessful = true
                } else {
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginState.value.isLoginSuccessful = false
                }

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    @Composable
    fun rememberFirebaseAuthLauncher(
        onAuthComplete: (AuthResult) -> Unit,
        onAuthError: (ApiException) -> Unit,
    ): ManagedActivityResultLauncher<Intent, ActivityResult> {
        val scope = rememberCoroutineScope()
        return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("GoogleAuth", "account $account")
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                scope.launch {
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    onAuthComplete(authResult)
                }
            } catch (e: ApiException) {
                Log.d("GoogleAuth", e.toString())
                onAuthError(e)
            }
        }
    }

}