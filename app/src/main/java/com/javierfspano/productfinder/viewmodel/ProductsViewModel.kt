package com.javierfspano.productfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierfspano.productfinder.data.ProductRepository
import com.javierfspano.productfinder.data.Result
import com.javierfspano.productfinder.model.Product
import com.javierfspano.productfinder.utils.exhaustive
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    private var _isLoading: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    val isLoading: MutableLiveData<Boolean> get() = _isLoading

    var shouldShowError: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }

    fun onSearchSubmitted(search: String) {

        viewModelScope.launch {
            isLoading.value = true
            val searchResults = productRepository.searchProducts(search)
            when (searchResults) {
                is Result.Success -> showResults(searchResults.value.results)
                is Result.GenericError,
                is Result.NetworkError -> showError()
            }.exhaustive
        }
    }

    private fun showError() {
        isLoading.value = false
        shouldShowError.value = true
    }

    fun showResults(results: List<Product>) {
        _productList.value = results
        isLoading.value = false
    }

}