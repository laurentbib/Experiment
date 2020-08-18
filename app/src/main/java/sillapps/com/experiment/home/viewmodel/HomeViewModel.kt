package sillapps.com.experiment.home.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sillapps.com.domain.usecase.GetRestaurantUseCase
import sillapps.com.experiment.app.BaseViewModel
import sillapps.com.experiment.home.contract.HomeViewEffect
import sillapps.com.experiment.home.contract.HomeViewEvent
import sillapps.com.experiment.home.contract.HomeViewState
import sillapps.com.experiment.utils.FetchStatus

class HomeViewModel(val getRestaurantUseCase: GetRestaurantUseCase) : BaseViewModel<HomeViewState, HomeViewEffect, HomeViewEvent>() {

    init {
        viewState = HomeViewState(
            fetchStatus = FetchStatus.Fetching,
            restaurants = emptyList()
        )
        getRestaurants()
    }

    override fun process(viewEvent: HomeViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is HomeViewEvent.OnResume -> {
                viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
            }
            is HomeViewEvent.RestaurantClicked -> {
                viewEffect = HomeViewEffect.GoToDetail(viewEvent.restaurant, viewEvent.view)
            }
            is HomeViewEvent.OnBottomReached -> {
                getRestaurants(true)
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