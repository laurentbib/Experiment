package sillapps.com.experiment.utils

import sillapps.com.domain.model.ErrorCode

sealed class FetchStatus {
    object NotFetched : FetchStatus()
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    data class Error(val msgRes: Int, val errorCode: ErrorCode) : FetchStatus()
}