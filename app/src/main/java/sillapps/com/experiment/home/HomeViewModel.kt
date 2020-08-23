package sillapps.com.experiment.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sillapps.com.domain.usecase.GetRestaurantUseCase
import sillapps.com.experiment.contract.BaseViewModel
import sillapps.com.experiment.utils.FetchStatus
import sillapps.com.experiment.view.LoadingView
import sillapps.com.experiment.view.RestaurantView

class HomeViewModel(val getRestaurantUseCase: GetRestaurantUseCase) : BaseViewModel<HomeViewState, HomeViewEffect, HomeViewEvent>() {

    init {
        viewState = HomeViewState(fetchStatus = FetchStatus.NotFetched, restaurants = emptyList())
    }

    override fun process(viewEvent: HomeViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is HomeViewEvent.Init -> {
                if (viewState.restaurants.isEmpty()) {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
                    getRestaurants()
                } else {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                }
            }
            is HomeViewEvent.OnSwipeRefresh -> getRestaurants()
            is HomeViewEvent.OnBottomReached -> getRestaurants(true)
            is HomeViewEvent.RestaurantClicked -> viewEffect = HomeViewEffect.GoToDetail(viewEvent.restaurant, viewEvent.views)
        }
    }

    private fun getRestaurants(forceNewCall: Boolean = false) {
        viewModelScope.launch {
            getRestaurantUseCase(forceNewCall).either({
                viewState = viewState.copy(fetchStatus = FetchStatus.NotFetched)
            }, { restaurants ->
                viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, restaurants = restaurants.map { RestaurantView(it) }.plus(LoadingView()))
            })
        }
    }
}