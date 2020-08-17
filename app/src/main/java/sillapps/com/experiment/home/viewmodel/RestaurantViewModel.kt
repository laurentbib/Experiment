package sillapps.com.experiment.home.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sillapps.com.domain.usecase.GetRestaurantUseCase
import sillapps.com.experiment.home.contract.FetchStatus
import sillapps.com.experiment.home.contract.MainViewEffect
import sillapps.com.experiment.home.contract.MainViewEvent
import sillapps.com.experiment.home.contract.MainViewState
import sillapps.com.experiment.utils.BaseViewModel

class RestaurantViewModel(val getRestaurantUseCase: GetRestaurantUseCase) : BaseViewModel<MainViewState, MainViewEffect, MainViewEvent>() {

    init {
        viewState = MainViewState(
            fetchStatus = FetchStatus.Fetching,
            restaurants = emptyList()
        )
        getRestaurants()
    }

    override fun process(viewEvent: MainViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is MainViewEvent.OnSwipeRefresh -> {
                getRestaurants(true)
            }
            is MainViewEvent.RestaurantClicked -> {
                viewEvent.restaurant
            }
        }
    }

    private fun getRestaurants(forceNewCall: Boolean = false) {
        viewModelScope.launch {
            getRestaurantUseCase(forceNewCall).either({
                viewState = viewState.copy(fetchStatus = FetchStatus.NotFetched)
            }, {
                viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, restaurants = it)
            })
        }
    }
}