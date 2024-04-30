package uk.ac.tees.mad.d3722574.repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import uk.ac.tees.mad.d3722574.data.User

object ProfileRepository {


    fun getProfileDetails(
        onSuccess: (User?) -> Unit,
        onFailure: () -> Unit
    ) {
        FirebaseFirestore.getInstance().collection("profile")
            .document(FirebaseAuth.getInstance().currentUser?.uid ?: "").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    if (it.result.exists()) {
                        val user = it.result.toObject(User::class.java)
                        if (user != null) {
                            onSuccess.invoke(user)
                        } else {
                            onSuccess.invoke(null)
                        }
                    }
                } else {
                    onFailure.invoke()
                }
            }
    }




    fun putProfileDetails(
        context: Context,
        user: User,
        isImageChanged : Boolean,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {

        val currentUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        if(isImageChanged) {
            FirebaseStorage.getInstance().reference.child("profile")
                .child(currentUid)
                .putFile(Uri.parse(user.profileUrl))
                .addOnCompleteListener { imageTask ->

                    if (imageTask.isSuccessful) {
                        FirebaseStorage.getInstance().reference.child("profile")
                            .child(currentUid).downloadUrl.addOnCompleteListener { downLoadTask ->


                                if (downLoadTask.isSuccessful) {

                                    val use = User(
                                        user.name,
                                        user.bio,
                                        downLoadTask.result.toString(),
                                        user.intrest
                                    )

                                    FirebaseFirestore.getInstance().collection("profile")
                                        .document(currentUid).set(use)
                                        .addOnCompleteListener { finalTask ->
                                            if (finalTask.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    "Profile Updated",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                                onSuccess.invoke()
                                            } else {
                                                Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT)
                                                    .show()
                                                onFailure.invoke()
                                            }
                                        }


                                } else {
                                    Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT)
                                        .show()
                                    onFailure.invoke()
                                }
                            }


                    } else {
                        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                        onFailure.invoke()
                    }

                }
        } else {
            val use = User(
                user.name,
                user.bio,
                user.profileUrl,
                user.intrest
            )

            FirebaseFirestore.getInstance().collection("profile")
                .document(currentUid).set(use)
                .addOnCompleteListener { finalTask ->
                    if (finalTask.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Profile Updated",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        onSuccess.invoke()
                    } else {
                        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT)
                            .show()
                        onFailure.invoke()
                    }
                }
        }




    }


}