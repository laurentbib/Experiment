package sillapps.com.data.response

import com.google.gson.annotations.SerializedName

data class GetRestaurantResponse(@SerializedName("restaurants") val restaurants: List<RestaurantResponse>)

data class RestaurantResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("available_at") val availableAt: String,
    @SerializedName("main_picture") val background: ImageResponse,
    @SerializedName("logo") val logo: ImageResponse,
    @SerializedName("grouped_order_discounts") val groupedOrderDiscounts: List<DiscountResponse>
)

data class ImageResponse(@SerializedName("url") val url: String)

data class DiscountResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("nb_people") val nbPeople: Int,
    @SerializedName("minimum_amount") val minimumAmount: Int,
    @SerializedName("discount_percent") val discountPercent: Int
)