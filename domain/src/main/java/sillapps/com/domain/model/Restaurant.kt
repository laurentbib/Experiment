package sillapps.com.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    val id: Int,
    val name: String,
    val availableAt: String,
    val mainPictureUrl: String,
    val logoUrl: String,
    val discounts: List<Discount>
) : Parcelable