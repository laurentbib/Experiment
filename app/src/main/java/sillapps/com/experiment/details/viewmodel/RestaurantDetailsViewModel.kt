package sillapps.com.experiment.details.viewmodel

import sillapps.com.experiment.app.BaseViewModel
import sillapps.com.experiment.details.contract.DetailsViewEffect
import sillapps.com.experiment.details.contract.DetailsViewEvent
import sillapps.com.experiment.details.contract.DetailsViewState
import sillapps.com.experiment.utils.FetchStatus

class RestaurantDetailsViewModel() : BaseViewModel<DetailsViewState, DetailsViewEffect, DetailsViewEvent>() {

    init {
        viewState = DetailsViewState(fetchStatus = FetchStatus.NotFetched)
    }

    override fun process(viewEvent: DetailsViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is DetailsViewEvent.Open -> {
                viewState = DetailsViewState(fetchStatus = FetchStatus.Fetched, restaurant = viewEvent.restaurant)
            }
            is DetailsViewEvent.BackPressClicked -> {
                viewEffect = DetailsViewEffect.Back
            }
        }
    }
}