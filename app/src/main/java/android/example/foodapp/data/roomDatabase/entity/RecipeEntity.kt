package android.example.foodapp.data.roomDatabase.entity

import android.example.foodapp.models.FoodRecipe
import android.example.foodapp.util.Constants.Companion.RECIPES_TABLE
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RECIPES_TABLE)
class RecipeEntity(
    var foodRecipe: FoodRecipe
) {
   @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}