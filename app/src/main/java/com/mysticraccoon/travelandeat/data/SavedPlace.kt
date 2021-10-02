package com.mysticraccoon.travelandeat.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mysticraccoon.travelandeat.core.TravelAndEatConstants.savedPlacesIdColumnName
import com.mysticraccoon.travelandeat.core.TravelAndEatConstants.savedPlacesTableName

@Entity(tableName = savedPlacesTableName)
data class SavedPlace(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = savedPlacesIdColumnName)
    val id: Int,
    val title: String = "",
    val dishName: String = "",
    var latitude: Double?,
    var longitude: Double?,
    val dishId: Int,
    val dishValue: Double,
    val dishThumb: String,
    val dishCategory: String,
    val checked: Boolean = false
)
