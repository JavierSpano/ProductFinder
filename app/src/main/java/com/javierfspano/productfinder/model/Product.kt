package com.javierfspano.productfinder.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: String,
    val title: String,
    val price: Float,
    @SerializedName("currency_id") val currency: Currency,
    val thumbnail: String,
    @SerializedName("available_quantity") val availableQuantity: Int,
    @SerializedName("sold_quantity") val soldQuantity: Int
) : Parcelable {
    enum class Currency {
        ARS,
        USD;
    }
}


