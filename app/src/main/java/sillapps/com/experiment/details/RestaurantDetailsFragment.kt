package sillapps.com.experiment.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.transition.addListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import sillapps.com.experiment.R
import sillapps.com.experiment.adapter.GenericAdapter
import sillapps.com.experiment.contract.BaseFragment
import sillapps.com.experiment.utils.FetchStatus
import sillapps.com.experiment.utils.fadeIn

class RestaurantDetailsFragment : BaseFragment<DetailsViewState, DetailsViewEffect, DetailsViewEvent, RestaurantDetailsViewModel>(R.layout.fragment_details) {

    override val viewModel: RestaurantDetailsViewModel by viewModel()

    private val args: RestaurantDetailsFragmentArgs by navArgs()

    private val glideListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            startPostponedEnterTransition()
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            if (restaurant_img?.drawable != null || restaurant_logo?.drawable != null) startPostponedEnterTransition()
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.transition_restaurant_details_shared)
        enterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.transition_restaurant_details).apply {
            addListener(onEnd = { back_button?.fadeIn(200) })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransitionNames()
    }

    override fun initUI() {
        viewModel.process(DetailsViewEvent.Init(args.restaurant))
        rw_discount?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        back_button?.setOnClickListener { viewModel.process(DetailsViewEvent.BackPressClicked) }
    }

    override fun renderViewState(viewState: DetailsViewState) {
        when (viewState.fetchStatus) {
            FetchStatus.Fetched -> {
                viewState.restaurant?.bind(requireView(), glideListener = glideListener)
                rw_discount?.adapter = GenericAdapter(viewState.discounts.toMutableList())
            }
        }
    }

    override fun renderViewEffect(viewEffect: DetailsViewEffect) {
        when (viewEffect) {
            is DetailsViewEffect.Back -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun setTransitionNames() {
        restaurant_img?.transitionName = getString(R.string.transition_img, args.restaurant.id)
        restaurant_info_background?.transitionName = getString(R.string.transition_info, args.restaurant.id)
        restaurant_logo?.transitionName = getString(R.string.transition_logo, args.restaurant.id)
    }
}