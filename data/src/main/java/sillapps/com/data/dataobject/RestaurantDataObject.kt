package sillapps.com.data.dataobject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import sillapps.com.data.utils.NO_ID

@Entity(tableName = "RESTAURANT")
data class RestaurantDataObject(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Int = NO_ID,
    @SerializedName("name")
    @ColumnInfo(name = "NAME") val name: String
)