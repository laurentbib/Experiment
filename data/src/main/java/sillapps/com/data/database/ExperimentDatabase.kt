package sillapps.com.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sillapps.com.data.dataobject.RestaurantDataObject

@Database(entities = [RestaurantDataObject::class], version = 1)
abstract class ExperimentDatabase : RoomDatabase() {
    abstract fun restaurantDAO(): RestaurantDAO
}