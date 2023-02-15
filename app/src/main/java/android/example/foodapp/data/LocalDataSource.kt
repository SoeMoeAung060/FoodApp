package android.example.foodapp.data

import android.example.foodapp.data.roomDatabase.RecipeDao
import android.example.foodapp.data.roomDatabase.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao
) {

    fun readDatabase() : Flow<List<RecipeEntity>>{
        return recipeDao.readRecipes()
    }

    fun insertRecipes(recipeEntity: RecipeEntity){
        recipeDao.insertRecipes(recipeEntity)
    }
}