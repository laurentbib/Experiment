package sillapps.com.experiment.details.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import sillapps.com.experiment.R
import sillapps.com.experiment.app.BaseFragment
import sillapps.com.experiment.details.contract.DetailsViewEffect
import sillapps.com.experiment.details.contract.DetailsViewEvent
import sillapps.com.experiment.details.contract.DetailsViewState
import sillapps.com.experiment.details.viewmodel.RestaurantDetailsViewModel
import sillapps.com.experiment.utils.FetchStatus
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
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(new_restaurant_img, "img ${args.restaurant.id}")
    }

    override fun initUI() {
        viewModel.process(DetailsViewEvent.Open(args.restaurant))
    }

    override fun renderViewState(viewState: DetailsViewState) {
        when (viewState.fetchStatus) {
            FetchStatus.Fetched -> {
                viewState.restaurant?.run {
                    new_restaurant_img?.loadImg(mainPictureUrl, glideListener)
                    new_restaurant_schedule?.text = availableAt
                    new_restaurant_name?.text = name
                    new_restaurant_location?.text = getString(R.string.format_location, Random.nextInt(1, 10), Random.nextInt(1, 20))
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