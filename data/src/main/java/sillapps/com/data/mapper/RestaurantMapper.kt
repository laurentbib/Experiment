package sillapps.com.data.mapper

import sillapps.com.data.databaseobject.DiscountDBObject
import sillapps.com.data.databaseobject.RestaurantDBObject
import sillapps.com.data.response.DiscountResponse
import sillapps.com.data.response.RestaurantResponse
import sillapps.com.data.utils.formatDate
import sillapps.com.domain.model.Discount
import sillapps.com.domain.model.Restaurant

class RestaurantMapper {

    fun restaurantToDB(restaurantResponse: RestaurantResponse) = RestaurantDBObject(
        name = restaurantResponse.name,
        description = restaurantResponse.description,
        availableAt = restaurantResponse.availableAt,
        backgroundUrl = restaurantResponse.background.url,
        logoUrl = restaurantResponse.logo.url
    )


    fun discountToDB(id: Int, discountResponse: DiscountResponse) = DiscountDBObject(
        restaurantId = id,
        nbPeople = discountResponse.nbPeople,
        minimumAmount = discountResponse.minimumAmount,
        discountPercent = discountResponse.discountPercent
    )

    fun restaurantFromDB(restaurantDataObject: RestaurantDBObject, discounts: List<DiscountDBObject>) = Restaurant(
        restaurantDataObject.id,
        restaurantDataObject.name,
        restaurantDataObject.description,
        restaurantDataObject.availableAt.formatDate(),
        restaurantDataObject.backgroundUrl,
        restaurantDataObject.logoUrl,
        discounts.map { discountFromDB(it) }
    )

    private fun discountFromDB(discountDBObject: DiscountDBObject) = Discount(
        discountDBObject.id,
        discountDBObject.restaurantId,
        discountDBObject.nbPeople,
        discountDBObject.minimumAmount,
        discountDBObject.discountPercent
    )
}