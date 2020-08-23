package sillapps.com.experiment.home

import android.view.View
import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.contract.AdapterItem
import sillapps.com.experiment.utils.FetchStatus

data class HomeViewState(val fetchStatus: FetchStatus, val restaurants: List<AdapterItem>)

sealed class HomeViewEffect {
    data class GoToDetail(val restaurant: Restaurant, val views: List<View>) : HomeViewEffect()
}

sealed class HomeViewEvent {
    object Init : HomeViewEvent()
    object OnSwipeRefresh : HomeViewEvent()
    object OnBottomReached : HomeViewEvent()
    data class RestaurantClicked(val restaurant: Restaurant, val views: List<View>) : HomeViewEvent()
}



