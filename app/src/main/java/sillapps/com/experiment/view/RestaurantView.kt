package sillapps.com.experiment.view

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.request.RequestListener
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.view_restaurant.view.*
import kotlinx.android.synthetic.main.view_restaurant.view.restaurant_img
import kotlinx.android.synthetic.main.view_restaurant.view.restaurant_info_background
import kotlinx.android.synthetic.main.view_restaurant.view.restaurant_location
import kotlinx.android.synthetic.main.view_restaurant.view.restaurant_logo
import kotlinx.android.synthetic.main.view_restaurant.view.restaurant_name
import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.R
import sillapps.com.experiment.contract.AdapterItem
import sillapps.com.experiment.utils.loadImg
import sillapps.com.experiment.utils.random

class RestaurantView(private val restaurant: Restaurant) : AdapterItem {

    override val layoutId = R.layout.view_restaurant

    fun bind(itemView: View, onClick: ((Restaurant, List<View>) -> Unit)? = null, glideListener: RequestListener<Drawable>? = null, withPlaceHolder: Boolean = false) = with(itemView) {
        setupTransition(itemView, restaurant.id)
        restaurant_img?.loadImg(restaurant.backgroundUrl, glideListener, withPlaceHolder)
        restaurant_logo?.loadImg(restaurant.logoUrl, glideListener, withPlaceHolder)
        restaurant_schedule?.text = restaurant.availableAt
        restaurant_name?.text = restaurant.name
        restaurant_description?.text = restaurant.description
        restaurant_location?.text = context.getString(R.string.format_location, random(), random())
        onClick?.let { setOnClickListener { it(restaurant, listOf(restaurant_img, restaurant_info_background, restaurant_logo)) } }
    }

    private fun setupTransition(itemView: View, id: Int) = with(itemView) {
        restaurant_img?.transitionName = context.getString(R.string.transition_img, id)
        restaurant_info_background?.transitionName = context.getString(R.string.transition_info, id)
        restaurant_logo?.transitionName = context.getString(R.string.transition_logo, id)
    }
}