package android.example.foodapp.data.roomDatabase.entity

import android.example.foodapp.models.FoodJoke
import android.example.foodapp.util.Constants.Companion.FOOD_JOKE_TABLE
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}