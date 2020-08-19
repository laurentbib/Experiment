package sillapps.com.experiment.details

import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.contract.AdapterItem
import sillapps.com.experiment.utils.FetchStatus
import sillapps.com.experiment.view.RestaurantView

data class DetailsViewState(val fetchStatus: FetchStatus, val restaurant: RestaurantView? = null, val discounts: List<AdapterItem> = emptyList())

sealed class DetailsViewEffect {
    object Back : DetailsViewEffect()
}

sealed class DetailsViewEvent {
    data class Init(val restaurant: Restaurant) : DetailsViewEvent()
    object BackPressClicked : DetailsViewEvent()
}

