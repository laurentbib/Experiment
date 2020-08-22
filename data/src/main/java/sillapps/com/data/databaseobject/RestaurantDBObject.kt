package sillapps.com.data.databaseobject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sillapps.com.data.utils.*

@Entity(tableName = "RESTAURANT")
data class RestaurantDBObject(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Int = NO_ID,
    @ColumnInfo(name = "NAME") val name: String,
    @ColumnInfo(name = "DESCRIPTION") val description: String,
    @ColumnInfo(name = "AVAILABLE_AT") val availableAt: String,
    @ColumnInfo(name = "BACKGROUND_URL") val backgroundUrl: String,
    @ColumnInfo(name = "LOGO_URL") val logoUrl: String
)