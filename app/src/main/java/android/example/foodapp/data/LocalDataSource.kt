package android.example.foodapp.data

import android.example.foodapp.data.roomDatabase.RecipeDao
import android.example.foodapp.data.roomDatabase.entity.FavoriteEntity
import android.example.foodapp.data.roomDatabase.entity.FoodJokeEntity
import android.example.foodapp.data.roomDatabase.entity.RecipeEntity
import android.example.foodapp.models.FoodJoke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao
) {

    fun readRecipes() : Flow<List<RecipeEntity>>{
        return recipeDao.readRecipes()
    }

    fun readFavoriteRecipes() : Flow<List<FavoriteEntity>>{
        return recipeDao.readFavoriteRecipes()
    }

    fun readFoodJoke() : Flow<List<FoodJokeEntity>>{
        return recipeDao.readFoodJoke()
    }

    suspend fun insertRecipes(recipeEntity: RecipeEntity){
        recipeDao.insertRecipes(recipeEntity)
    }

    suspend fun insertFavoriteRecipes(favoriteEntity: FavoriteEntity){
        recipeDao.insertFavoriteRecipes(favoriteEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        recipeDao.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteFavoriteRecipes(favoriteEntity: FavoriteEntity){
        recipeDao.deleteFavoriteRecipe(favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipes(){
        recipeDao.deleteAllFavoriteRecipes()
    }
}