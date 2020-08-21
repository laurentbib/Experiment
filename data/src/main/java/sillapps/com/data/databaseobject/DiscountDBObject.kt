package sillapps.com.data.databaseobject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import sillapps.com.data.utils.NO_ID

@Entity(
    tableName = "DISCOUNT",
    foreignKeys = [ForeignKey(
        entity = RestaurantDBObject::class,
        parentColumns = arrayOf("ID"),
        childColumns = arrayOf("RESTAURANT_ID"),
        onDelete = CASCADE
    )]
)
data class DiscountDBObject(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Int = NO_ID,
    @ColumnInfo(name = "RESTAURANT_ID", index = true) val restaurantId: Int,
    @ColumnInfo(name = "NB_PEOPLE") val nbPeople: Int,
    @ColumnInfo(name = "MIN_AMOUNT") val minimumAmount: Int,
    @ColumnInfo(name = "DISCOUNT_PERCENT") val discountPercent: Int
)