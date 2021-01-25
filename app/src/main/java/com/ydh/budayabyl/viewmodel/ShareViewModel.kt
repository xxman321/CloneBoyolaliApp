package com.ydh.budayabyl.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ydh.budayabyl.model.Site
import com.ydh.budayabyl.model.User
import kotlinx.coroutines.Job


// cara passing data tanpa navigation
class ShareViewModel : ViewModel() {
    val userMutableLiveData by lazy { MutableLiveData<User>() }
    val productMutableLivedata by lazy { MutableLiveData<Site>() }
    private val jobs by lazy { mutableListOf<Job>() }

    //set data dari Domain
    fun setUserdata(user: User) {
        userMutableLiveData.value = user
    }

    fun onClear() {
        jobs.forEach { if (!it.isCompleted) it.cancel() }
    }

}