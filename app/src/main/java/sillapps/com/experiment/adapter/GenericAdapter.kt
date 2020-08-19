package sillapps.com.experiment.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.contract.AdapterItem
import sillapps.com.experiment.utils.calledOnce
import sillapps.com.experiment.utils.inflate
import sillapps.com.experiment.view.DiscountView
import sillapps.com.experiment.view.LoadingView
import sillapps.com.experiment.view.RestaurantView
import kotlin.contracts.ExperimentalContracts

class GenericAdapter(
    private val adapterItems: MutableList<AdapterItem> = mutableListOf(),
    val onRestaurantClick: ((Restaurant, List<View>) -> Unit)? = null,
    val onBottomReached: (() -> Unit)? = null
) : RecyclerView.Adapter<GenericAdapter.GenericViewHolder>() {

    fun addRestaurants(restaurants: List<AdapterItem>) {
        this.adapterItems.removeAll { it is LoadingView }
        this.adapterItems.addAll(restaurants)
        notifyDataSetChanged()
    }

    override fun getItemCount() = adapterItems.size

    override fun getItemViewType(position: Int) = adapterItems[position].layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenericViewHolder(parent.inflate(viewType))

    @ExperimentalContracts
    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) = holder.bind(adapterItems[position])

    inner class GenericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @ExperimentalContracts
        fun bind(adapterItem: AdapterItem) {
            when (adapterItem) {
                is RestaurantView -> adapterItem.bind(itemView, onRestaurantClick)
                is DiscountView -> adapterItem.bind(itemView, adapterItem == adapterItems.first(), adapterItem == adapterItems.last())
                is LoadingView -> calledOnce { onBottomReached?.invoke() }
            }
        }
    }
}