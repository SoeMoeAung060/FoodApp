package android.example.foodapp.data.network

import android.example.foodapp.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {
    @GET("/recipes/complexSearch")
     fun getRecipes(
        @QueryMap queries : Map<String, String>
    ):Response<FoodRecipe>
}