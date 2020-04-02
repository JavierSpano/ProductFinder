package com.javierfspano.productfinder.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.javierfspano.productfinder.R
import com.javierfspano.productfinder.databinding.ProductSearchItemBinding
import com.javierfspano.productfinder.model.Product

class ProductsRecyclerAdapter(private val glide: RequestManager) :
    ListAdapter<Product, ProductsRecyclerAdapter.ViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_search_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { product ->
            holder.binding.root.transitionName = "root_${product.id}"
            holder.binding.product = product
            //executePendingBindings()
            holder.binding.setOnClickListener { goToProductScreen(holder.binding, product) }
            glide.load(product.thumbnail).placeholder(R.drawable.ic_image)
                .into(holder.binding.productThumbnail)
        }
    }

    private fun goToProductScreen(binding: ProductSearchItemBinding, product: Product) {
        val directions = SearchFragmentDirections.actionSearchFragmentToProductFragment(product)
        val extras =
            FragmentNavigatorExtras(binding.root to "root_${product.id}")

        binding.root.findNavController().navigate(directions, extras)
    }

    inner class ViewHolder(val binding: ProductSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.title == oldItem.title
                && oldItem.price == oldItem.price
    }

}