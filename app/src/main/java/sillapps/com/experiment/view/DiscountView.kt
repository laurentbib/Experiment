package sillapps.com.experiment.view

import android.view.View
import kotlinx.android.synthetic.main.view_discount.view.*
import sillapps.com.domain.model.Discount
import sillapps.com.experiment.R
import sillapps.com.experiment.contract.AdapterItem

class DiscountView(private val discount: Discount) : AdapterItem {

    override val layoutId = R.layout.view_discount

    fun bind(itemView: View, isFirst: Boolean, isLast: Boolean) = with(itemView) {
        nb_people?.text = context.getString(R.string.restaurant_details_nb_people, discount.nbPeople)
        min_amount?.text = context.getString(R.string.restaurant_details_min_amount, discount.minimumAmount)
        discount_percent?.text = context.getString(R.string.restaurant_details_discount, discount.discountPercent)
        val linesVisibilities = when {
            isFirst -> View.GONE to View.VISIBLE
            isLast -> View.VISIBLE to View.GONE
            else -> View.VISIBLE to View.VISIBLE
        }
        left_line?.visibility = linesVisibilities.first
        right_line?.visibility = linesVisibilities.second
    }
}