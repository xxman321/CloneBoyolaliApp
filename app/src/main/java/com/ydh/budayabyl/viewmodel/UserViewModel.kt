package com.ydh.budayabyl.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ydh.budayabyl.model.User
import com.ydh.budayabyl.service.FirebaseSiteService
import com.ydh.budayabyl.service.FirebaseUserService
import com.ydh.budayabyl.utils.SharedPrefHelper
import com.ydh.budayabyl.viewmodel.state.SiteState
import com.ydh.budayabyl.viewmodel.state.UserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(
    private val context: Context,
    private val service: FirebaseUserService
) : ViewModel() {

    private val auth by lazy { Firebase.auth }
    private val sharedPref by lazy { SharedPrefHelper(context) }

    private val mutableState by lazy { MutableLiveData<UserState>() }
    val state: LiveData<UserState> get() = mutableState

    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile

    fun getUser(userId: String) {
        mutableState.value = UserState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userProfile.value = service.getProfileData(userId)
            } catch (ex: Exception) {
                onError(ex)
            }
        }
    }

    fun registerUser(email: String, password: String, username: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                mutableState.value = UserState.Loading()

                it.user?.sendEmailVerification()

                val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

                val userData = hashMapOf(
                    "userId" to firebase.uid,
                    "userName" to username,
                    "profileImage" to ""
                )

                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        service.registerUser(userData, firebase.uid)
                        mutableState.postValue(UserState.SuccessRegisterUser("success"))
                    } catch (ex: Exception) {
                        onError(ex)
                    }
                }

            }.addOnFailureListener {
                onError(it)
            }
    }

    fun loginWithGoogle(activity: Activity){
        mutableState.value = UserState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = GoogleSignIn.getClient(activity, service.loginUser())
                mutableState.postValue(UserState.SuccessRegisterUser("success"))

            }catch (ex: Exception){
                onError(ex)
            }
        }
    }


    fun resetPassword(email: String) {
        mutableState.value = UserState.Loading()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    mutableState.postValue(UserState.SuccessSendResetPassword(email))

                } else {
//                    showMessage("something wrong")
                }
            }
    }

    fun loginUser(email: String, password: String) {
        mutableState.value = UserState.Loading()
        auth.signInWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            if (it.user?.isEmailVerified == true) {

                val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                sharedPref.isLogin = true
                mutableState.postValue(UserState.SuccessLoginUser(email))

            } else {
                mutableState.postValue(
                    UserState.Failed("Email belum diverifikasi")
                )
            }
        }.addOnFailureListener {
            onError(it)
        }
    }

    private fun onError(exc: Exception) {
        exc.printStackTrace()
        mutableState.postValue(UserState.Error(exc))
    }


}