package com.ydh.budayabyl.viewmodel.state

import com.ydh.budayabyl.model.Site
import com.ydh.budayabyl.model.User

sealed class SiteState {
    data class Loading(val message: String = "Loading...") : SiteState()
    data class Error(val exception: Exception) : SiteState()
    data class SuccessGetAllSite(val list: List<Site>) : SiteState()
    data class SuccessGetSite(val site: Site) : SiteState()
}