package com.javierfspano.productfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.javierfspano.productfinder.data.ProductRepository
import com.javierfspano.productfinder.model.Product
import com.javierfspano.productfinder.viewmodel.ProductsViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProductViewModelTest {


    @Mock
    private lateinit var repository: ProductRepository

    private lateinit var viewModel: ProductsViewModel

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var productListObserver: Observer<List<Product>>

    private val sampleSearch = "moto"

    private val emptySearch = ""

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
        viewModel = ProductsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when search is submitted starts loading`() {
        viewModel.isLoading.observeForever(loadingObserver)
        argumentCaptor<Boolean>().apply {
            viewModel.onSearchSubmitted(sampleSearch)
            verify(loadingObserver, times(2)).onChanged(this.capture())
        }

    }

    @Test
    fun `when search is submitted product list is updated`() {
        viewModel.productList.observeForever(productListObserver)
        argumentCaptor<List<Product>>().apply {
            viewModel.onSearchSubmitted(sampleSearch)
            verify(productListObserver, times(1)).onChanged(capture())
        }
    }


}