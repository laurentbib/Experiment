package sillapps.com.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sillapps.com.data.databaseobject.DiscountDBObject

@Dao
interface DiscountDAO {
    @Query("SELECT * FROM DISCOUNT WHERE RESTAURANT_ID = :restaurantId")
    fun getDiscounts(restaurantId: Int): List<DiscountDBObject>

    @Insert
    fun insertAll(discounts: List<DiscountDBObject>)

    @Query("DELETE FROM DISCOUNT")
    fun purge()
}