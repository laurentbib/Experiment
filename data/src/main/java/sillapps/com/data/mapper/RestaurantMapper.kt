package sillapps.com.data.mapper

import sillapps.com.data.databaseobject.DiscountDBObject
import sillapps.com.data.databaseobject.RestaurantDBObject
import sillapps.com.data.response.DiscountResponse
import sillapps.com.data.response.RestaurantResponse
import sillapps.com.data.utils.EMPTY_STR
import sillapps.com.domain.model.Discount
import sillapps.com.domain.model.Restaurant
import java.text.SimpleDateFormat
import java.util.*

class RestaurantMapper {

    fun restaurantToDB(restaurantResponse: RestaurantResponse) = RestaurantDBObject(
        name = restaurantResponse.name,
        availableAt = restaurantResponse.availableAt,
        mainPictureUrl = restaurantResponse.mainPicture.url,
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
        restaurantDataObject.availableAt.formatDate(),
        restaurantDataObject.mainPictureUrl,
        restaurantDataObject.logoUrl,
        discounts.map { discountFromDB(it) }
    )

    private fun discountFromDB(discountDBObject: DiscountDBObject) = Discount(
        discountDBObject.restaurantId,
        discountDBObject.nbPeople,
        discountDBObject.minimumAmount,
        discountDBObject.discountPercent
    )

    private fun String.formatDate(): String {
        val inFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return outFormat.format(inFormat.parse(this) ?: EMPTY_STR)
    }
}