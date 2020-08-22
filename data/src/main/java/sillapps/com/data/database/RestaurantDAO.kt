package sillapps.com.data.database

import androidx.room.Dao
import androidx.room.Query
import sillapps.com.data.databaseobject.RestaurantDBObject

@Dao
interface RestaurantDAO : BaseDao<RestaurantDBObject> {
    @Query("SELECT * FROM RESTAURANT")
    fun getAll(): List<RestaurantDBObject>

    @Query("DELETE FROM RESTAURANT")
    fun purge()
}