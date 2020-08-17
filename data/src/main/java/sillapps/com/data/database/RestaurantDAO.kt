package sillapps.com.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sillapps.com.data.dataobject.RestaurantDataObject

@Dao
interface RestaurantDAO {
    @Query("SELECT * FROM RESTAURANT")
    fun getRestaurants(): List<RestaurantDataObject>

    @Insert
    fun insertAll(restaurants: List<RestaurantDataObject>)
}