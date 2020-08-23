package sillapps.com.experiment.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.useless_error.*
import org.koin.android.viewmodel.ext.android.viewModel
import sillapps.com.experiment.R
import sillapps.com.experiment.adapter.GenericAdapter
import sillapps.com.experiment.contract.BaseFragment
import sillapps.com.experiment.utils.*

class HomeFragment : BaseFragment<HomeViewState, HomeViewEffect, HomeViewEvent, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        waitForTransition(rw_restaurant)
    }

    override fun initUI() {
        viewModel.process(HomeViewEvent.Init)
        rw_restaurant?.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = GenericAdapter(
                onRestaurantClick = { restaurant, views -> viewModel.process(HomeViewEvent.RestaurantClicked(restaurant, views)) },
                onBottomReached = { viewModel.process(HomeViewEvent.OnBottomReached) }
            )
        }
        swipe_refresh?.setOnRefreshListener { viewModel.process(HomeViewEvent.OnSwipeRefresh) }
    }

    override fun renderViewState(viewState: HomeViewState) {
        when (viewState.fetchStatus) {
            FetchStatus.Fetching -> if (viewState.restaurants.isEmpty()) rw_restaurant_shimmer?.beginShimmerAnim()
            FetchStatus.Fetched -> {
                swipe_refresh?.isRefreshing = false
                rw_restaurant_shimmer?.endShimmerAnim()
                (rw_restaurant?.adapter as? GenericAdapter)?.updateRestaurants(viewState.restaurants)
            }
            is FetchStatus.Error -> {
                swipe_refresh?.isRefreshing = false
                rw_restaurant_shimmer?.endShimmerAnim()
                activity?.showSnackbar(viewState.fetchStatus.msgRes)
                uselessError(north, east, west)
            }
        }
    }

    override fun renderViewEffect(viewEffect: HomeViewEffect) {
        when (viewEffect) {
            is HomeViewEffect.GoToDetail -> {
                val extras = FragmentNavigatorExtras(*viewEffect.views.map { it to it.transitionName }.toTypedArray())
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToRestaurantDetailsFragment(
                        viewEffect.restaurant
                    ), extras
                )
            }
        }
    }
}