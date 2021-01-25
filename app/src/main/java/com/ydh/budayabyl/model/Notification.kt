package com.ydh.budayabyl.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(
    val id: String? = null,
    val title: String? = null,
    val content: String? = null
): Parcelable {
    var isViewed = false
    companion object {
        fun DocumentSnapshot.toNotification(): Notification? {
            return try {
//                val nameEn = getString("nameId")!!
                val title = getString("title") ?: ""
                val body = getString("body") ?: ""
                Notification(id, title, body)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting notification", e)
                FirebaseCrashlytics.getInstance().log("Error converting notification")
                FirebaseCrashlytics.getInstance().setCustomKey("notificationId", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
        private const val TAG = "Notification"
    }
}