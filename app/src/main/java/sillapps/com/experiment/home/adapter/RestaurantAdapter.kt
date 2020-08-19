package sillapps.com.experiment.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_restaurant.view.*
import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.R
import sillapps.com.experiment.utils.inflate
import sillapps.com.experiment.utils.loadImg
import kotlin.random.Random

class RestaurantAdapter(
    private val restaurants: MutableList<Restaurant> = mutableListOf(),
    val onClick: (Restaurant, List<View>) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    fun addRestaurants(restaurants: List<Restaurant>) {
        this.restaurants.addAll(restaurants)
        notifyDataSetChanged()
    }

    override fun getItemCount() = restaurants.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RestaurantViewHolder(parent.inflate(R.layout.view_restaurant))

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) = holder.bind(restaurants[position])

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: Restaurant) = with(itemView) {
            setupTransition(restaurant.id)
            restaurant_img?.loadImg(restaurant.mainPictureUrl)
            restaurant_logo?.loadImg(restaurant.logoUrl)
            restaurant_schedule?.text = restaurant.availableAt
            restaurant_name?.text = restaurant.name
            restaurant_location?.text = context.getString(R.string.format_location, Random.nextInt(1, 10), Random.nextInt(1, 20))
            setOnClickListener { onClick(restaurant, listOf(restaurant_img, restaurant_info_background, restaurant_logo)) }
        }

        private fun setupTransition(id: Int) = with(itemView) {
            restaurant_img?.transitionName = context.getString(R.string.transition_img, id)
            restaurant_info_background?.transitionName = context.getString(R.string.transition_info, id)
            restaurant_logo?.transitionName = context.getString(R.string.transition_logo, id)
        }
    }
}