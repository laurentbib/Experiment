package sillapps.com.data.contract

import sillapps.com.data.database.RestaurantDAO
import sillapps.com.data.mapper.RestaurantMapper
import sillapps.com.data.webservices.RetrofitHelper
import sillapps.com.domain.contract.RestaurantRepository
import sillapps.com.domain.model.ErrorCode
import sillapps.com.domain.model.Restaurant
import sillapps.com.domain.model.Either

class RestaurantRepositoryImpl(private val restaurantDAO: RestaurantDAO, private val restaurantMapper: RestaurantMapper) : RestaurantRepository {
    override suspend fun getRestaurants(forceNewCall: Boolean): Either<ErrorCode, List<Restaurant>> {
        val restaurants = restaurantDAO.getRestaurants().map { restaurantMapper.fromDB(it) }
        return if (forceNewCall || restaurants.isEmpty()) {
            when (val call = RetrofitHelper.getRestaurants()) {
                is Either.Successful -> {
                    restaurantDAO.insertAll(call.success.restaurants)
                    Either.Successful(restaurantDAO.getRestaurants().map { restaurantMapper.fromDB(it) })
                }
                is Either.Failure -> {
                    Either.Failure(call.fail)
                }
            }
        } else {
            Either.Successful(restaurants)
        }
    }
}