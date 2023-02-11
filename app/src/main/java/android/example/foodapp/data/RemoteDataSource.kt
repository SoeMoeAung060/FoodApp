package android.example.foodapp.data

import android.example.foodapp.data.network.FoodRecipesApi
import android.example.foodapp.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

     fun getRecipes(queries : Map<String, String>) : Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }
}