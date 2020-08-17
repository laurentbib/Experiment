package sillapps.com.experiment.home.contract

import sillapps.com.domain.model.Restaurant

data class MainViewState(val fetchStatus: FetchStatus, val restaurants: List<Restaurant>)

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}

sealed class MainViewEffect {
    data class ShowSnackbar(val message: String) : MainViewEffect()
    data class ShowToast(val message: String) : MainViewEffect()
}

sealed class MainViewEvent {
    data class RestaurantClicked(val restaurant: Restaurant) : MainViewEvent()
    object OnSwipeRefresh : MainViewEvent()
    object FetchRestaurants : MainViewEvent()
}

internal interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}

