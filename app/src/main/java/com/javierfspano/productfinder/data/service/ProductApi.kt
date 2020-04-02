package com.javierfspano.productfinder.data.service

import com.javierfspano.productfinder.model.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("/sites/MLA/search")
    suspend fun searchProduct(@Query("q") search: String): SearchResults
}