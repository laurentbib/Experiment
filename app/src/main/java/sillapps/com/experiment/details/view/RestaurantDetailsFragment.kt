package sillapps.com.experiment.details.view

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
import sillapps.com.experiment.app.BaseFragment
import sillapps.com.experiment.details.adapter.DiscountAdapter
import sillapps.com.experiment.details.contract.DetailsViewEffect
import sillapps.com.experiment.details.contract.DetailsViewEvent
import sillapps.com.experiment.details.contract.DetailsViewState
import sillapps.com.experiment.details.viewmodel.RestaurantDetailsViewModel
import sillapps.com.experiment.utils.FetchStatus
import sillapps.com.experiment.utils.fadeIn
import sillapps.com.experiment.utils.loadImg
import kotlin.random.Random

class RestaurantDetailsFragment : BaseFragment<DetailsViewState, DetailsViewEffect, DetailsViewEvent, RestaurantDetailsViewModel>(R.layout.fragment_details) {

    override val viewModel: RestaurantDetailsViewModel by viewModel()

    private val args: RestaurantDetailsFragmentArgs by navArgs()

    private val glideListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            startPostponedEnterTransition()
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            startPostponedEnterTransition()
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
        restaurant_img?.transitionName = getString(R.string.transition_img, args.restaurant.id)
        restaurant_info_background?.transitionName = getString(R.string.transition_info, args.restaurant.id)
        restaurant_logo?.transitionName = getString(R.string.transition_logo, args.restaurant.id)
    }

    override fun initUI() {
        viewModel.process(DetailsViewEvent.Open(args.restaurant))
        rw_discount?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        back_button?.setOnClickListener { viewModel.process(DetailsViewEvent.BackPressClicked) }
    }

    override fun renderViewState(viewState: DetailsViewState) {
        when (viewState.fetchStatus) {
            FetchStatus.Fetched -> {
                viewState.restaurant?.run {
                    restaurant_img?.loadImg(mainPictureUrl, glideListener)
                    restaurant_logo?.loadImg(logoUrl, glideListener)
                    restaurant_name?.text = name
                    restaurant_location?.text = getString(R.string.format_location, Random.nextInt(1, 10), Random.nextInt(1, 20))
                    rw_discount?.adapter = DiscountAdapter(discounts)
                }
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
}