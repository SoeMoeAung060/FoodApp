package android.example.foodapp.viewModels

import android.app.Application
import android.example.foodapp.data.DataStoreRepository
import android.example.foodapp.util.Constants.Companion.API_KEY
import android.example.foodapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import android.example.foodapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import android.example.foodapp.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import android.example.foodapp.util.Constants.Companion.QUERY_ADD_RECIEPE_INFORMATION
import android.example.foodapp.util.Constants.Companion.QUERY_ADD_RECIEPE_NUTRITION
import android.example.foodapp.util.Constants.Companion.QUERY_API_KEY
import android.example.foodapp.util.Constants.Companion.QUERY_DIET
import android.example.foodapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import android.example.foodapp.util.Constants.Companion.QUERY_MAX_READY_TIME
import android.example.foodapp.util.Constants.Companion.QUERY_NUMBER
import android.example.foodapp.util.Constants.Companion.QUERY_SEARCH
import android.example.foodapp.util.Constants.Companion.QUERY_TYPE
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
    ) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType(mealType : String, mealTypeId : Int, dietType : String, dietTypeId : Int){

        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun saveBackOnline(backOnline: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }

    fun applyQueries() : HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()


        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIEPE_NUTRITION] = "true"
        queries[QUERY_ADD_RECIEPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        queries[QUERY_MAX_READY_TIME] = "20"

        return queries
    }

    fun applySearchQuery(searchQuery : String) : HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()

        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIEPE_NUTRITION] = "true"
        queries[QUERY_ADD_RECIEPE_INFORMATION] = "true"
        return queries
    }

    fun showNetworkStatus(){
        if (!networkStatus){
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus){
             if (backOnline){
                 Toast.makeText(getApplication(), "Internet Connected", Toast.LENGTH_SHORT).show()
                 saveBackOnline(false)
             }
        }
    }
}