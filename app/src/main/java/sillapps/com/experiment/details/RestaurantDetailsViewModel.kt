package sillapps.com.experiment.details

import sillapps.com.experiment.contract.BaseViewModel
import sillapps.com.experiment.utils.FetchStatus
import sillapps.com.experiment.view.DiscountView
import sillapps.com.experiment.view.RestaurantView

class RestaurantDetailsViewModel : BaseViewModel<DetailsViewState, DetailsViewEffect, DetailsViewEvent>() {

    init {
        viewState = DetailsViewState(fetchStatus = FetchStatus.NotFetched)
    }

    override fun process(viewEvent: DetailsViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is DetailsViewEvent.Init -> {
                viewState = DetailsViewState(
                    fetchStatus = FetchStatus.Fetched,
                    restaurant = RestaurantView(viewEvent.restaurant),
                    discounts = viewEvent.restaurant.discounts.map { DiscountView(it) }
                )
            }
            is DetailsViewEvent.BackPressClicked -> {
                viewEffect = DetailsViewEffect.Back
            }
        }
    }
}