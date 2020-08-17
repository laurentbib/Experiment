package sillapps.com.data.dataobject

import com.google.gson.annotations.SerializedName

data class GetRestaurantResponse(@SerializedName("restaurants") val restaurants: List<RestaurantDataObject>)