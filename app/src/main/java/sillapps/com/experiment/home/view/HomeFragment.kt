package sillapps.com.experiment.home.view

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import sillapps.com.experiment.R
import sillapps.com.experiment.app.BaseFragment
import sillapps.com.experiment.home.adapter.RestaurantAdapter
import sillapps.com.experiment.home.contract.HomeViewEffect
import sillapps.com.experiment.home.contract.HomeViewEvent
import sillapps.com.experiment.home.contract.HomeViewState
import sillapps.com.experiment.home.viewmodel.HomeViewModel
import sillapps.com.experiment.utils.FetchStatus

class HomeFragment : BaseFragment<HomeViewState, HomeViewEffect, HomeViewEvent, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        viewModel.process(HomeViewEvent.OnResume)
    }

    override fun initUI() {
        rw_restaurant?.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = RestaurantAdapter { restaurant, view -> viewModel.process(HomeViewEvent.RestaurantClicked(restaurant, view)) }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(1)) viewModel.process(HomeViewEvent.OnBottomReached)
                }
            })
        }
    }

    override fun renderViewState(viewState: HomeViewState) {
        when (viewState.fetchStatus) {
            FetchStatus.Fetching -> {
                progress?.visibility = View.VISIBLE
            }
            FetchStatus.Fetched -> {
                progress?.visibility = View.GONE
                (rw_restaurant?.adapter as? RestaurantAdapter)?.addRestaurants(viewState.restaurants)
            }
        }
    }

    override fun renderViewEffect(viewEffect: HomeViewEffect) {
        when (viewEffect) {
            is HomeViewEffect.GoToDetail -> {
                val extras = FragmentNavigatorExtras(viewEffect.view to "img ${viewEffect.restaurant.id}")
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRestaurantDetailsFragment(viewEffect.restaurant), extras)
            }
            is HomeViewEffect.ShowToast -> {
                Toast.makeText(context, viewEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}