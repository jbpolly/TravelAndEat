package com.mysticraccoon.travelandeat.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mysticraccoon.travelandeat.core.utils.TravelAndEatConstants.savedPlacesIdColumnName
import com.mysticraccoon.travelandeat.core.utils.TravelAndEatConstants.savedPlacesTableName
import kotlinx.parcelize.Parcelize

@Entity(tableName = savedPlacesTableName)
@Parcelize
data class SavedPlace(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = savedPlacesIdColumnName)
    var id: Int = 0,
    var title: String = "",
    var dishName: String = "",
    var latitude: Double? = null,
    var longitude: Double? = null,
    var dishId: String = "",
    var dishValue: String = "0.0",
    var dishThumb: String = "",
    var dishCategory: String = "",
    var location: String = "",
    var checked: Boolean = false
): Parcelable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SavedPlace

        if (id != other.id) return false
        if (title != other.title) return false
        if (dishName != other.dishName) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (dishId != other.dishId) return false
        if (dishValue != other.dishValue) return false
        if (dishThumb != other.dishThumb) return false
        if (dishCategory != other.dishCategory) return false
        if (location != other.location) return false
        if (checked != other.checked) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + dishName.hashCode()
        result = 31 * result + (latitude?.hashCode() ?: 0)
        result = 31 * result + (longitude?.hashCode() ?: 0)
        result = 31 * result + dishId.hashCode()
        result = 31 * result + dishValue.hashCode()
        result = 31 * result + dishThumb.hashCode()
        result = 31 * result + dishCategory.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + checked.hashCode()
        return result
    }
}


