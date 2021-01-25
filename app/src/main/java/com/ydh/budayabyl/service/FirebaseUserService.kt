package com.ydh.budayabyl.service

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ydh.budayabyl.model.User
import com.ydh.budayabyl.model.User.Companion.toUser
import kotlinx.coroutines.tasks.await

object FirebaseUserService {
    private const val TAG = "FirebaseProfileService"
    private val auth by lazy { Firebase.auth }


    suspend fun getProfileData(userId: String): User? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("users")
                .document(userId).get().await().toUser()

        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error getting user details")
//            FirebaseCrashlytics.getInstance().setCustomKey("user id", xpertSlug)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }

    suspend fun loginUser(): GoogleSignInOptions{
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("YOUR_WEB_APPLICATION_CLIENT_ID")
            .requestEmail()
            .build()

    }

    suspend fun registerUser(userData: HashMap<String,String>, userId: String){
        val db = FirebaseFirestore.getInstance()
        try {
            db.collection("users").document(userId).set(userData)

            }catch (e: Exception){
            Log.e(TAG, "Error register user", e)
            FirebaseCrashlytics.getInstance().log("Error register user")
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }
}