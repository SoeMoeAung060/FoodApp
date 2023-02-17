package android.example.foodapp.data.roomDatabase

import android.example.foodapp.data.roomDatabase.entity.FavoriteEntity
import android.example.foodapp.data.roomDatabase.entity.RecipeEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    //RecipeEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipeEntity: RecipeEntity)

    //FavoriteEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipes(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipeEntity>>


    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes() : Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()
}