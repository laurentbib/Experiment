package sillapps.com.domain.contract

import sillapps.com.domain.model.Either
import sillapps.com.domain.model.ErrorCode
import sillapps.com.domain.model.Restaurant

interface RestaurantRepository {
    suspend fun getRestaurants(forceNewCall: Boolean): Either<ErrorCode, List<Restaurant>>
}