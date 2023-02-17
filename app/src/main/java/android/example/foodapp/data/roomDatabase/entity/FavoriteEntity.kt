package android.example.foodapp.data.roomDatabase.entity

import android.example.foodapp.util.Constants.Companion.FAVORITE_RECIPES_TABLE
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.example.foodapp.models.Result


@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var result: Result
    )