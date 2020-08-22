package sillapps.com.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sillapps.com.data.databaseobject.DiscountDBObject
import sillapps.com.data.databaseobject.RestaurantDBObject

@Dao
interface DiscountDAO: BaseDao<DiscountDBObject> {
    @Query("SELECT * FROM DISCOUNT")
    fun getAll(): List<DiscountDBObject>

    @Query("SELECT * FROM DISCOUNT WHERE RESTAURANT_ID = :restaurantId")
    fun getByRestaurantId(restaurantId: Int): List<DiscountDBObject>

    @Query("DELETE FROM DISCOUNT")
    fun purge()
}