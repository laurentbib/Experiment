package sillapps.com.experiment.view

import android.view.View
import kotlinx.android.synthetic.main.fragment_details.view.*
import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.R
import sillapps.com.experiment.contract.AdapterItem
import sillapps.com.experiment.utils.loadImg
import sillapps.com.experiment.utils.random

class RestaurantDetailsView(private val restaurant: Restaurant) : AdapterItem {

    override val layoutId = R.layout.fragment_details

    fun bind(itemView: View, onClick: ((Restaurant, List<View>) -> Unit)? = null) = with(itemView) {
        setupTransition(itemView, restaurant.id)
        restaurant_img?.loadImg(restaurant.mainPictureUrl)
        restaurant_logo?.loadImg(restaurant.logoUrl)
        restaurant_name?.text = restaurant.name
        restaurant_location?.text = context.getString(R.string.format_location, random(), random())
        onClick?.let { setOnClickListener { it(restaurant, listOf(restaurant_img, restaurant_info_background, restaurant_logo)) } }
    }

    private fun setupTransition(itemView: View, id: Int) = with(itemView) {
        restaurant_img?.transitionName = context.getString(R.string.transition_img, id)
        restaurant_info_background?.transitionName = context.getString(R.string.transition_info, id)
        restaurant_logo?.transitionName = context.getString(R.string.transition_logo, id)
    }
}