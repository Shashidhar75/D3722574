package uk.ac.tees.mad.d3722574.ui.presentation.signupscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.base.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.repository.GlobalConstants
import uk.ac.tees.mad.d3722574.repository.Screen
import uk.ac.tees.mad.d3722574.viewmodel.UserViewModel

object SignupDestination : NavigationDestination {
    override val routeName: String
        get() = "signup"
}

@Composable
fun SignupScren(
    onNavigateToLogin: () -> Unit, onHomeScreen: () -> Unit,
    registrationViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val registrationState = remember { registrationViewModel.registrationState }
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    val launcher = registrationViewModel.rememberFirebaseAuthLauncher(onAuthComplete = { result ->
        user = result.user
        GlobalConstants.user = user
        onHomeScreen()
    }, onAuthError = {
        GlobalConstants.user = null
    })

    val isActive = remember {
        mutableStateOf(false)
    }
    val name = remember {
        mutableStateOf("Hello pro")
    }

    val email = remember {
        mutableStateOf("email@gmail.com")
    }

    val phone = remember {
        mutableStateOf("+91-XXX-XXX-XXXXX")
    }

    val password = remember {
        mutableStateOf("password@123")
    }

    val confirmPassword = remember {
        mutableStateOf("password@123")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    registrationViewModel.checkUser(GlobalConstants.user, Screen.SIGNUP_SCREEN)

    if(registrationState.collectAsState().value.isRegistrationSuccessful){

        LaunchedEffect(key1 = true) {
            onHomeScreen.invoke()
        }
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),

            ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.size(40.dp))
                Text(
                    text = "Create an account",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = "Name", style = MaterialTheme.typography.bodyMedium, color = Color.Black
                )
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    },
                    shape = RoundedCornerShape(10.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0084FE),
                        unfocusedBorderColor = Color(0xFF0084FE),
                        focusedLabelColor = Color(0xFF0084FE),
                        errorSupportingTextColor = Color(0xFFC65B52),
                        errorBorderColor = Color(0xFFC65B52),
                        errorSuffixColor = Color(0xFFC65B52),
                        errorLabelColor = Color(0xFFC65B52),
                        errorLeadingIconColor = Color(0xFFC65B52),
                        errorTextColor = Color(0xFFC65B52),


                        ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                    ),

                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "email")
                    }

                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Email", style = MaterialTheme.typography.bodyMedium, color = Color.Black
                )
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    shape = RoundedCornerShape(10.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0084FE),
                        unfocusedBorderColor = Color(0xFF0084FE),
                        focusedLabelColor = Color(0xFF0084FE),
                        errorSupportingTextColor = Color(0xFFC65B52),
                        errorBorderColor = Color(0xFFC65B52),
                        errorSuffixColor = Color(0xFFC65B52),
                        errorLabelColor = Color(0xFFC65B52),
                        errorLeadingIconColor = Color(0xFFC65B52),
                        errorTextColor = Color(0xFFC65B52),


                        ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    ),

                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = "email")
                    }

                )
                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Phone", style = MaterialTheme.typography.bodyMedium, color = Color.Black
                )
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                    value = phone.value,
                    onValueChange = {
                        phone.value = it
                    },
                    shape = RoundedCornerShape(10.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0084FE),
                        unfocusedBorderColor = Color(0xFF0084FE),
                        focusedLabelColor = Color(0xFF0084FE),
                        errorSupportingTextColor = Color(0xFFC65B52),
                        errorBorderColor = Color(0xFFC65B52),
                        errorSuffixColor = Color(0xFFC65B52),
                        errorLabelColor = Color(0xFFC65B52),
                        errorLeadingIconColor = Color(0xFFC65B52),
                        errorTextColor = Color(0xFFC65B52),


                        ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                    ),

                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Phone, contentDescription = "email")
                    }

                )
                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    value = password.value,
                    onValueChange = {
                        password.value = it
                    },

                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = "email")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0084FE),
                        unfocusedBorderColor = Color(0xFF0084FE),
                        focusedLabelColor = Color(0xFF0084FE),
                        errorSupportingTextColor = Color(0xFFC65B52),
                        errorBorderColor = Color(0xFFC65B52),
                        errorSuffixColor = Color(0xFFC65B52),
                        errorLabelColor = Color(0xFFC65B52),
                        errorLeadingIconColor = Color(0xFFC65B52),
                        errorTextColor = Color(0xFFC65B52),
                    ),
                    shape = RoundedCornerShape(10.dp),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {

                            val visibleIconAndText = Pair(
                                first = Icons.Filled.Visibility, second = "Password Visible"
                            )

                            val hiddenIconAndText = Pair(
                                first = Icons.Filled.VisibilityOff, second = "Password Hidden"
                            )

                            val passwordVisibilityIconAndText =
                                if (isPasswordVisible) visibleIconAndText else hiddenIconAndText

                            // Render Icon
                            Icon(
                                imageVector = passwordVisibilityIconAndText.first,
                                contentDescription = passwordVisibilityIconAndText.second
                            )
                        }
                    },
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                )
                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Confirm Password",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    value = confirmPassword.value,
                    onValueChange = {
                        confirmPassword.value = it
                        if (confirmPassword == password) {
                            isActive.value = true
                        }
                    },

                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = "email")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0084FE),
                        unfocusedBorderColor = Color(0xFF0084FE),
                        focusedLabelColor = Color(0xFF0084FE),
                        errorSupportingTextColor = Color(0xFFC65B52),
                        errorBorderColor = Color(0xFFC65B52),
                        errorSuffixColor = Color(0xFFC65B52),
                        errorLabelColor = Color(0xFFC65B52),
                        errorLeadingIconColor = Color(0xFFC65B52),
                        errorTextColor = Color(0xFFC65B52),
                    ),
                    shape = RoundedCornerShape(10.dp),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {

                            val visibleIconAndText = Pair(
                                first = Icons.Filled.Visibility, second = "Password Visible"
                            )

                            val hiddenIconAndText = Pair(
                                first = Icons.Filled.VisibilityOff, second = "Password Hidden"
                            )

                            val passwordVisibilityIconAndText =
                                if (isPasswordVisible) visibleIconAndText else hiddenIconAndText

                            // Render Icon
                            Icon(
                                imageVector = passwordVisibilityIconAndText.first,
                                contentDescription = passwordVisibilityIconAndText.second
                            )
                        }
                    },
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                )
                Spacer(modifier = Modifier.size(20.dp))

                Button(
                    onClick = {
                        registrationViewModel.signUpUser(context, email.value, password.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF0084FE))
                ) {
                    Text(text = "Sign Up", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(25.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    HorizontalDivider(
                        modifier = Modifier.width(170.dp), thickness = 1.dp
                    )
                    Text(
                        text = " or ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(), thickness = 1.dp
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        registrationViewModel.signInWithGoogle(context, launcher)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.googleg_standard_color_18),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Continue with Google",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                    )
                    Text(text = "Login Here",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF0084FE),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            onNavigateToLogin()
                        })
                }
            }
        }
    }
}


@Preview
@Composable
private fun View() {
    SignupScren({}, {})
}