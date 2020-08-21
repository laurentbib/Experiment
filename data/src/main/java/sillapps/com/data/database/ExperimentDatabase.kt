package sillapps.com.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sillapps.com.data.databaseobject.DiscountDBObject
import sillapps.com.data.databaseobject.RestaurantDBObject

@Database(entities = [RestaurantDBObject::class, DiscountDBObject::class], version = 1, exportSchema = false)
abstract class ExperimentDatabase : RoomDatabase() {
    abstract fun restaurantDAO(): RestaurantDAO
    abstract fun discountDAO(): DiscountDAO
}