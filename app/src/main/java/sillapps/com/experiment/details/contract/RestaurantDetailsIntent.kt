package sillapps.com.experiment.details.contract

import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.utils.FetchStatus

data class DetailsViewState(val fetchStatus: FetchStatus, val restaurant: Restaurant? = null)

sealed class DetailsViewEffect {
    object Back : DetailsViewEffect()
}

sealed class DetailsViewEvent {
    data class Open(val restaurant: Restaurant) : DetailsViewEvent()
    object BackPressClicked : DetailsViewEvent()
}

