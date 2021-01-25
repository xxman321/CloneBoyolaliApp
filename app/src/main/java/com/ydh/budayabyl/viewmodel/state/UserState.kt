package com.ydh.budayabyl.viewmodel.state

import com.ydh.budayabyl.model.User

sealed class UserState {
    data class Loading(val message: String = "Loading...") : UserState()
    data class Error(val exception: Exception) : UserState()
    data class SuccessGetAllUser(val list: List<User>) : UserState()
    data class SuccessGetUser(val user: User) : UserState()
    data class SuccessSendResetPassword(val email: String) : UserState()
    data class SuccessRegisterUser(val msg: String) : UserState()
    data class SuccessLoginUser(val msg: String) : UserState()
    data class Failed(val msg: String) : UserState()
}