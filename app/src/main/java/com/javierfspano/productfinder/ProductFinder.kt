package com.javierfspano.productfinder

import android.app.Application
import com.javierfspano.productfinder.di.appModule
import com.javierfspano.productfinder.di.productServiceModule
import com.javierfspano.productfinder.di.productViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProductFinder : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProductFinder)
            modules(listOf(appModule, productViewModelModule, productServiceModule))
        }
    }
}