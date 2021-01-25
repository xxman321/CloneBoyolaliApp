package com.ydh.budayabyl.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userId: String? = null,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val photo: String? = null,
    val account: String? = null,
    val role: String? = null
): Parcelable
{
    companion object {
        fun DocumentSnapshot.toUser(): User? {
            return try {
                val name = getString("name")!!
                val username = getString("username")!!
                val email = getString("email")!!
                val photo = getString("imgUrl")!!
                User(id, name, username, email, photo)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting user profile", e)
                FirebaseCrashlytics.getInstance().log("Error converting user profile")
                FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
        private const val TAG = "User"
    }
}