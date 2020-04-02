package com.javierfspano.productfinder.data

import com.javierfspano.productfinder.data.service.ProductApi

class ProductRepository(val productApi: ProductApi) {

    suspend fun searchProducts(search: String) = getResult { productApi.searchProduct(search) }
}
