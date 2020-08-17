package sillapps.com.data.mapper

import sillapps.com.data.dataobject.RestaurantDataObject
import sillapps.com.domain.model.Restaurant

class RestaurantMapper {
    fun fromDB(restaurantDataObject: RestaurantDataObject): Restaurant {
        return Restaurant(restaurantDataObject.name)
    }
}