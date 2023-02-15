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
import android.example.foodapp.util.Constants.Companion.QUERY_TYPE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
    ) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType : String, mealTypeId : Int, dietType : String, dietTypeId : Int){

        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
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
//
//        queries[QUERY_QUERY] = "pasta"
//        queries[QUERY_MAXFAT] = "25"


        return queries
    }
}