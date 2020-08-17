package sillapps.com.domain.usecase

import sillapps.com.domain.contract.RestaurantRepository
import sillapps.com.domain.model.Either
import sillapps.com.domain.model.ErrorCode
import sillapps.com.domain.model.Restaurant

class GetRestaurantUseCase(private val restaurantRepository: RestaurantRepository) : UseCase<Boolean, ErrorCode, List<Restaurant>>() {

    override suspend fun run(params: Boolean?): Either<ErrorCode, List<Restaurant>> {
        return restaurantRepository.getRestaurants(params ?: false)
    }
}