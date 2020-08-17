package sillapps.com.experiment.home.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import sillapps.com.experiment.R
import sillapps.com.experiment.home.contract.FetchStatus
import sillapps.com.experiment.home.contract.MainViewEffect
import sillapps.com.experiment.home.contract.MainViewEvent
import sillapps.com.experiment.home.contract.MainViewState
import sillapps.com.experiment.utils.BaseActivity
import sillapps.com.experiment.home.viewmodel.RestaurantViewModel

class HomeActivity : BaseActivity<MainViewState, MainViewEffect, MainViewEvent, RestaurantViewModel>() {

    override val viewModel: RestaurantViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun renderViewState(viewState: MainViewState) {
        when (viewState.fetchStatus) {
            FetchStatus.Fetching -> {
            }
            FetchStatus.Fetched -> {
                progress?.visibility = View.GONE
                test?.text = viewState.restaurants.joinToString(separator = " - ") { it.name }
            }
            FetchStatus.NotFetched -> {
            }
        }
    }

    override fun renderViewEffect(viewEffect: MainViewEffect) {
        when (viewEffect) {
            MainViewEffect.ShowSnackbar("test") -> {
            }
            MainViewEffect.ShowToast("test") -> {
            }
        }
    }
}