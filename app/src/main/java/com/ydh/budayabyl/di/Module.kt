package com.ydh.budayabyl.di

import com.ydh.budayabyl.service.FirebaseSiteService
import com.ydh.budayabyl.service.FirebaseUserService
import com.ydh.budayabyl.viewmodel.SiteViewModel
import com.ydh.budayabyl.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val firebaseModule = module {
    fun provideSiteService(): FirebaseSiteService = FirebaseSiteService

    fun provideUserService(): FirebaseUserService = FirebaseUserService

    single { provideSiteService()}
    single { provideUserService()}
}

val viewModelModule = module {
    viewModel {
        SiteViewModel( get())
    }
    viewModel {
        UserViewModel(androidContext(), get())
    }
}
