package com.ydh.budayabyl.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Site (
    val siteId: String? = null,
    val name_id: String? = null,
//    val name_en: String? = null,
    val description: String? = null,
    val image: String? = null,
    val link: String? = null,
    val recommended: String? = null
//    var latitude: Double? = 0.0,
//    var longitude: Double? = 0.0
): Parcelable{
    companion object {
        fun DocumentSnapshot.toSite(): Site? {
            return try {
//                val nameEn = getString("nameId")!!
                val nameId = getString("nama")?: ""
                val description = getString("keterangan")?: ""
                val image = getString("gambar")?: ""
                val link = getString("lokasi")?: ""
                val recommended = getString("recommended")?: ""
//                var latitude = getDouble("latitude")!!
//                var longitude = getDouble("longitude")
                Site(id, nameId, description, image, link, recommended)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting site", e)
                FirebaseCrashlytics.getInstance().log("Error converting site")
                FirebaseCrashlytics.getInstance().setCustomKey("siteId", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
        private const val TAG = "User"
    }
}