package com.javierfspano.productfinder.di

import com.javierfspano.productfinder.BuildConfig
import com.javierfspano.productfinder.data.ProductRepository
import com.javierfspano.productfinder.data.service.ProductApi
import com.javierfspano.productfinder.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { ProductRepository(get()) }

}

val productViewModelModule = module {

    viewModel { ProductsViewModel(get()) }

}

val productServiceModule = module {

    single { provideRetrofit() }

    single { provideProductApi(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideProductApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)
