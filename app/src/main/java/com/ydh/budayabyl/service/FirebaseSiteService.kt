package com.ydh.budayabyl.service

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.ydh.budayabyl.model.Site
import com.ydh.budayabyl.model.Site.Companion.toSite
import com.ydh.budayabyl.model.User
import com.ydh.budayabyl.model.User.Companion.toUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object FirebaseSiteService {
    private const val TAG = "FirebaseSiteService"

    suspend fun getSite(siteId: String): Site? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("tempat")
                .document(siteId).get().await().toSite()

        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error getting user details")
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }

    @ExperimentalCoroutinesApi
    fun getRecommendedList(): Flow<List<Site>> {
        val db = FirebaseFirestore.getInstance()
        return callbackFlow {
            val listenerRegistration = db.collection("tempat")
                .whereEqualTo("recommended", "true")
                .addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                    if (firebaseFirestoreException != null) {
                        cancel(message = "Error fetching posts",
                            cause = firebaseFirestoreException)
                        return@addSnapshotListener
                    }
                    val map = querySnapshot!!.mapNotNull { it.toSite() }
                    offer(map)
                }
            awaitClose {
                Log.d(TAG, "Cancelling posts listener")
                listenerRegistration.remove()
            }
        }
    }

    suspend fun getAllList(): List<Site> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("tempat")
                .get()
                .await()
                .documents.mapNotNull { it.toSite() }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting  list", e)
            FirebaseCrashlytics.getInstance().log("Error getting list")
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }

    suspend fun getSiteList(category: String): List<Site> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("tempat")
                .whereEqualTo("category", category)
                .get()
                .await()
                .documents.mapNotNull { it.toSite() }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting $category list", e)
            FirebaseCrashlytics.getInstance().log("Error getting $category list")
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }

    suspend fun getSearch(query: String): List<Site> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("sites")
                .whereEqualTo("name", query)
                .get()
                .await()
                .documents.mapNotNull { it.toSite() }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting $query list", e)
            FirebaseCrashlytics.getInstance().log("Error getting $query list")
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }
}