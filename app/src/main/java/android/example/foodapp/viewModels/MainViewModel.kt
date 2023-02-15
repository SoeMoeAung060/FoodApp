package android.example.foodapp.viewModels

import android.app.Application
import android.content.Context
import android.example.foodapp.data.Repository
import android.example.foodapp.data.roomDatabase.RecipeEntity
import android.example.foodapp.models.FoodRecipe
import android.example.foodapp.util.NetworkResult
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application){

    /** ROOM DATABASE */
    val readRecipes : LiveData<List<RecipeEntity>> = repository.local.readDatabase().asLiveData()
    
    private fun insertRecipes(recipeEntity: RecipeEntity) {
        viewModelScope.launch(Dispatchers.IO) { 
            repository.local.insertRecipes(recipeEntity)
        }
    }


    /** RETROFIT */
    var recipesResponse : MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

     private suspend fun getRecipesSafeCall(queries: Map<String, String>){
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)
                
                /** For Room Database*/
                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null){
                    offlineCacheRecipes(foodRecipe)
                }
            }catch (e: java.lang.Exception){
                recipesResponse.value= NetworkResult.Error("Recipes Not Found")
            }
        }else{
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipeEntity = RecipeEntity(foodRecipe)
        insertRecipes(recipeEntity)
    }


    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code()==402 -> {
                return NetworkResult.Error("API Key Limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes Not Found")
            }
            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResult.Success(foodRecipe!!)
            }
            else ->
                return NetworkResult.Error(response.message())
        }
    }


    private fun hasInternetConnection() : Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}