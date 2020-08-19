package sillapps.com.data.repository

import sillapps.com.data.database.DiscountDAO
import sillapps.com.data.database.RestaurantDAO
import sillapps.com.data.mapper.RestaurantMapper
import sillapps.com.data.webservices.RetrofitHelper
import sillapps.com.domain.contract.RestaurantRepository
import sillapps.com.domain.model.Either
import sillapps.com.domain.model.ErrorCode
import sillapps.com.domain.model.Restaurant

class RestaurantRepositoryImpl(
    private val restaurantDAO: RestaurantDAO,
    private val discountDAO: DiscountDAO,
    private val restaurantMapper: RestaurantMapper
) : RestaurantRepository {

    override suspend fun getRestaurants(forceNewCall: Boolean): Either<ErrorCode, List<Restaurant>> {
        val restaurants = getData()
        return if (forceNewCall || restaurants.isEmpty()) {
            when (val call = RetrofitHelper.getRestaurants()) {
                is Either.Successful -> {
                    call.success.restaurants.forEach { restaurantResponse ->
                        val insertedId = restaurantDAO.insert(restaurantMapper.restaurantToDB(restaurantResponse))
                        discountDAO.insertAll(restaurantResponse.groupedOrderDiscounts.map { restaurantMapper.discountToDB(insertedId.toInt(), it) })
                    }
                    Either.Successful(getData())
                }
                is Either.Failure -> {
                    Either.Failure(call.fail)
                }
            }
        } else {
            Either.Successful(restaurants)
        }
    }

    private fun getData() = restaurantDAO.getRestaurants().map {
        val discounts = discountDAO.getDiscounts(it.id)
        restaurantMapper.restaurantFromDB(it, discounts)
    }
}