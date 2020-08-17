package sillapps.com.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sillapps.com.domain.model.Either

abstract class UseCase<in Params, out FailureType, out SuccessType> {

    abstract suspend fun run(params: Params? = null): Either<FailureType, SuccessType>

    suspend operator fun invoke(params: Params? = null): Either<FailureType, SuccessType> {
        return withContext(Dispatchers.IO) { run(params) }
    }
}