package com.javierfspano.productfinder.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.javierfspano.productfinder.R
import com.javierfspano.productfinder.utils.dpAsPixels
import com.javierfspano.productfinder.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {

    val viewModel by sharedViewModel<ProductsViewModel>()

    val loadingDialog by lazy {
        Dialog(requireContext(), R.style.CircularDialog).apply {
            setCancelable(false)
            this.setContentView(ProgressBar(requireContext()).apply {
                setPadding(dpAsPixels(8, resources.displayMetrics.density))
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureEnterTransition()
    }

    private fun configureEnterTransition() {
        sharedElementEnterTransition = MaterialContainerTransform(requireContext()).apply {
            duration = 400
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        val adapter = ProductsRecyclerAdapter(Glide.with(this))
        setupRecyclerView(adapter)
        setupObservers(adapter)
    }

    private fun setupRecyclerView(adapter: ProductsRecyclerAdapter) {
        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
    }

    private fun setupSearchView() {
        searchView.requestFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.onSearchSubmitted(it)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun setupObservers(adapter: ProductsRecyclerAdapter) {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it)
                loadingDialog.show()
            else
                loadingDialog.dismiss()
        })

        viewModel.productList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.shouldShowError.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(
                    searchView,
                    getString(R.string.product_search_error),
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.shouldShowError.value = false
            }
        })
    }

}
