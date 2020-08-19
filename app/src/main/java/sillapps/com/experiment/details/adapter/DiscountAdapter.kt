package sillapps.com.experiment.details.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_discount.view.*
import sillapps.com.domain.model.Discount
import sillapps.com.experiment.R
import sillapps.com.experiment.utils.inflate

class DiscountAdapter(private val discounts: List<Discount>) : RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder>() {

    override fun getItemCount() = discounts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DiscountViewHolder(parent.inflate(R.layout.view_discount))

    override fun onBindViewHolder(holder: DiscountViewHolder, position: Int) = holder.bind(discounts[position])

    inner class DiscountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(discount: Discount) = with(itemView) {
            nb_people?.text = context.getString(R.string.restaurant_details_nb_people, discount.nbPeople)
            min_amount?.text = context.getString(R.string.restaurant_details_min_amount, discount.minimumAmount)
            discount_percent?.text = context.getString(R.string.restaurant_details_discount, discount.discountPercent)
            val linesVisibilities = when (discount) {
                discounts.first() -> View.GONE to View.VISIBLE
                discounts.last() -> View.VISIBLE to View.GONE
                else -> View.VISIBLE to View.VISIBLE
            }
            left_line?.visibility = linesVisibilities.first
            right_line?.visibility = linesVisibilities.second
        }
    }
}