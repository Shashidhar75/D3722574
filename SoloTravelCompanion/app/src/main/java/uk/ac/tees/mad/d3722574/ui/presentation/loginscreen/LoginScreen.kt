package uk.ac.tees.mad.d3722574.ui.presentation.loginscreen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.d3722574.R
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.viewmodel.UserViewModel


object LoginDestination : NavigationDestination {
    override val routeName: String
        get() = "login"
}

@Composable
fun LoginScreen(
    onNavigateToRegistration: () -> Unit,
    onNavigateToHomeScreen: () -> Unit

) {
    val context = LocalContext.current
    val loginViewModel: UserViewModel = hiltViewModel()
    val loginState = loginViewModel.loginStatus.value

    LaunchedEffect(loginState) {
        if(loginState) {
            onNavigateToHomeScreen.invoke()
        }
    }


    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val remembered = remember {
        mutableStateOf(false)
    }

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
            Spacer(modifier = Modifier.size(100.dp))
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = "Welcome back to the app",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = "Email Address",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            OutlinedTextField(
                modifier = Modifier
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
            Spacer(modifier = Modifier.size(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }

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
                            first = Icons.Filled.Visibility,
                            second = "Password Visible"
                        )

                        val hiddenIconAndText = Pair(
                            first = Icons.Filled.VisibilityOff,
                            second = "Password Hidden"
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
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
            )

            Spacer(modifier = Modifier.size(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = remembered.value, onCheckedChange = {
                        remembered.value = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF0084FE),
                    )
                )
                Text(
                    text = "keep me signed in ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

            }

            Button(
                onClick = {
                    loginViewModel.loginUser(context, email.value, password.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_blue)))
             {
                Text(text = "Login", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Create an account",
                style = MaterialTheme.typography.bodyLarge,
                color =colorResource(id = R.color.primary_blue),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onNavigateToRegistration()
                    }
            )
        }
    }
}

@Preview
@Composable
private fun View() {
    LoginScreen({}, {})
}