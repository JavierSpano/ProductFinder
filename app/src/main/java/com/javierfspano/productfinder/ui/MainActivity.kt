package com.javierfspano.productfinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.MaterialContainerTransformSharedElementCallback
import com.javierfspano.productfinder.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        configureTransitions()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun configureTransitions() {
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    }

}
