package sillapps.com.experiment.home.contract

import android.view.View
import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.utils.FetchStatus

data class HomeViewState(val fetchStatus: FetchStatus, val restaurants: List<Restaurant>)

sealed class HomeViewEffect {
    data class GoToDetail(val restaurant: Restaurant, val view: View) : HomeViewEffect()
    data class ShowToast(val message: String) : HomeViewEffect()
}

sealed class HomeViewEvent {
    object OnResume : HomeViewEvent()
    data class RestaurantClicked(val restaurant: Restaurant, val view: View) : HomeViewEvent()
    object OnBottomReached : HomeViewEvent()
}



