package sillapps.com.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sillapps.com.data.databaseobject.RestaurantDBObject

@Dao
interface RestaurantDAO {
    @Query("SELECT * FROM RESTAURANT")
    fun getRestaurants(): List<RestaurantDBObject>

    @Insert
    fun insert(restaurants: RestaurantDBObject): Long

    @Query("DELETE FROM RESTAURANT")
    fun purge()
}