package com.ydh.budayabyl.viewmodel

import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.ydh.budayabyl.service.FirebaseSiteService
import com.ydh.budayabyl.service.FirebaseUserService
import com.ydh.budayabyl.viewmodel.state.SiteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SiteViewModel(
    private val service: FirebaseSiteService
): ViewModel() {

    private val mutableState by lazy { MutableLiveData<SiteState>() }
    val state: LiveData<SiteState> get() = mutableState
    private val jobs by lazy { mutableListOf<Job>() }

    fun getSiteList(category: String){
        mutableState.value = SiteState.Loading()
        jobs.add(

            viewModelScope.launch(Dispatchers.IO) {
            try {
                val siteList = service.getSiteList(category)
                mutableState.postValue(SiteState.SuccessGetAllSite(siteList))
            }catch (ex: Exception){
                onError(ex)
            }
        }
        )
    }

    fun getAllList(){
        mutableState.value = SiteState.Loading()
        jobs.add(

            viewModelScope.launch(Dispatchers.IO){
            try {
                val siteList = service.getAllList()
                mutableState.postValue(SiteState.SuccessGetAllSite(siteList))
            }catch (ex: Exception){
                onError(ex)
            }
        }
        )
    }

    fun getSite(siteId: String){
        mutableState.value = SiteState.Loading()
        jobs.add(

            viewModelScope.launch(Dispatchers.IO) {
            try {
                val site = service.getSite(siteId)
                mutableState.postValue(site?.let { SiteState.SuccessGetSite(it) })
            }catch (ex: Exception){
                onError(ex)
            }
        }
        )
    }

    private fun onError(exc: Exception) {
        exc.printStackTrace()
        mutableState.postValue(SiteState.Error(exc))
    }

    fun onClear() {
        jobs.forEach { if (!it.isCompleted) it.cancel() }
    }

}
