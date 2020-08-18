package sillapps.com.experiment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_restaurant.view.*
import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.R
import sillapps.com.experiment.utils.loadImg
import kotlin.random.Random

class RestaurantAdapter(
    private val restaurants: MutableList<Restaurant> = mutableListOf(),
    val onClick: (Restaurant, View) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    fun addRestaurants(restaurants: List<Restaurant>) {
        this.restaurants.addAll(restaurants)
        notifyDataSetChanged()
    }

    override fun getItemCount() = restaurants.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RestaurantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_restaurant, parent, false))

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) = holder.bind(restaurants[position])

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: Restaurant) = with(itemView) {
            ViewCompat.setTransitionName(restaurant_img, "img ${restaurant.id}")
            restaurant_img?.loadImg(restaurant.mainPictureUrl)
            restaurant_schedule?.text = restaurant.availableAt
            restaurant_name?.text = restaurant.name
            restaurant_location?.text = context.getString(R.string.format_location, Random.nextInt(1, 10), Random.nextInt(1, 20))
            setOnClickListener { onClick(restaurant, restaurant_img) }
        }
    }
}