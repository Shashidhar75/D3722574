package uk.ac.tees.mad.d3722574.repository

import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.auth.FirebaseUser

class GlobalConstants {
    companion object {

        var user: FirebaseUser? = null
            get() = field
            set(value) { field = value }

        var email_user :User?= null
            get() = field
            set(value) {field= value}

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            get() = field
            set(value) { field = value }
    }

}

enum class Screen{
    LOGIN_SCREEN,SIGNUP_SCREEN
}