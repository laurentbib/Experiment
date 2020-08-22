package sillapps.com.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Discount(
    val id: Int,
    val restaurantId: Int,
    val nbPeople: Int,
    val minimumAmount: Int,
    val discountPercent: Int
) : Parcelable