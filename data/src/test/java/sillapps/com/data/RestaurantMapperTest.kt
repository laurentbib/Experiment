package sillapps.com.data

import org.junit.Assert.assertEquals
import org.junit.Test
import sillapps.com.data.databaseobject.DiscountDBObject
import sillapps.com.data.databaseobject.RestaurantDBObject
import sillapps.com.data.mapper.RestaurantMapper
import sillapps.com.data.response.DiscountResponse
import sillapps.com.data.response.ImageResponse
import sillapps.com.data.response.RestaurantResponse
import sillapps.com.data.utils.*
import sillapps.com.domain.model.Discount
import sillapps.com.domain.model.Restaurant

class RestaurantMapperTest {

    private val restaurantMapper = RestaurantMapper()

    private val discountResponse = DiscountResponse(DISCOUNT_ID, NB_PEOPLE, MIN_AMOUNT, DISCOUNT_PERCENT)
    private val restaurantResponse = RestaurantResponse(
        REST_ID,
        REST_NAME,
        REST_DESC,
        REST_AVAILABILITY,
        ImageResponse(BG_URL),
        ImageResponse(LOGO_URL),
        listOf(discountResponse, discountResponse)
    )

    private val discountDBObject = DiscountDBObject(DISCOUNT_ID, REST_ID, NB_PEOPLE, MIN_AMOUNT, DISCOUNT_PERCENT)
    private val restaurantDBObject = RestaurantDBObject(REST_ID, REST_NAME, REST_DESC, REST_AVAILABILITY, BG_URL, LOGO_URL)

    private val discount = Discount(DISCOUNT_ID, REST_ID, NB_PEOPLE, MIN_AMOUNT, DISCOUNT_PERCENT)
    private val restaurant = Restaurant(REST_ID, REST_NAME, REST_DESC, REST_FORMATED_HOUR, BG_URL, LOGO_URL, listOf(discount, discount))

    @Test
    fun restaurantFromDB() {
        assertEquals(restaurantDBObject, restaurantMapper.restaurantToDB(restaurantResponse))
    }

    @Test
    fun discountToDB() {
        assertEquals(discountDBObject, restaurantMapper.discountToDB(REST_ID, discountResponse))
    }

    @Test
    fun restaurantToDB() {
        assertEquals(restaurant, restaurantMapper.restaurantFromDB(restaurantDBObject, listOf(discountDBObject, discountDBObject)))
    }
}