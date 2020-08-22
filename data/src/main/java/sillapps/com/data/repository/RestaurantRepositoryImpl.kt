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
        val restaurants = getDataFromDB()
        return if (forceNewCall || restaurants.isEmpty()) {
            launchWSandGetDataFromDB()
        } else {
            Either.Successful(restaurants)
        }
    }

    private fun getDataFromDB() = restaurantDAO.getAll().map {
        val discounts = discountDAO.getByRestaurantId(it.id)
        restaurantMapper.restaurantFromDB(it, discounts)
    }

    private suspend fun launchWSandGetDataFromDB(): Either<ErrorCode, List<Restaurant>> {
        return when (val call = RetrofitHelper.getRestaurants()) {
            is Either.Successful -> {
                call.success.restaurants.forEach { restaurantResponse ->
                    val insertedId = restaurantDAO.insert(restaurantMapper.restaurantToDB(restaurantResponse))
                    discountDAO.insertAll(restaurantResponse.groupedOrderDiscounts.map { restaurantMapper.discountToDB(insertedId.toInt(), it) })
                }
                Either.Successful(getDataFromDB())
            }
            is Either.Failure -> Either.Failure(call.fail)
        }
    }
}