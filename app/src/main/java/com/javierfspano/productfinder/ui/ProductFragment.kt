package com.javierfspano.productfinder.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import com.javierfspano.productfinder.R
import com.javierfspano.productfinder.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding

    private val args: ProductFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_product, container, false)
        binding.product = args.product

        loadImage(binding)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.transitionName = "root_${args.product.id}"
    }

    private fun configureEnterTransition() {
        sharedElementEnterTransition = MaterialContainerTransform(requireContext())
    }

    private fun loadImage(binding: FragmentProductBinding) {
        Glide.with(this)
            .load(args.product.thumbnail)
            .placeholder(R.drawable.ic_image)
            .into(binding.productImage)
    }
}
