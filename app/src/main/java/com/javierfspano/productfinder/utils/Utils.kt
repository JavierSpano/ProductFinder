package com.javierfspano.productfinder.utils

fun dpAsPixels(sizeInDp: Int, density: Float) = (sizeInDp * density + 0.5f).toInt()


// property extension to add at the end of 'when' to force it to implement all cases
val <T> T.exhaustive: T
    get() = this