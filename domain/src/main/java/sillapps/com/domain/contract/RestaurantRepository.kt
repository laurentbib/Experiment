package sillapps.com.domain.contract

import sillapps.com.domain.model.ErrorCode
import sillapps.com.domain.model.Restaurant
import sillapps.com.domain.model.Either

interface RestaurantRepository {
    suspend fun getRestaurants(forceNewCall: Boolean): Either<ErrorCode, List<Restaurant>>
}