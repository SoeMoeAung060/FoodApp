package android.example.foodapp.data

import android.content.Context
import android.example.foodapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import android.example.foodapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import android.example.foodapp.util.Constants.Companion.PREFERENCES_DIET_TYPE
import android.example.foodapp.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import android.example.foodapp.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import android.example.foodapp.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import android.example.foodapp.util.Constants.Companion.PREFERENCES_NAME
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

//Four Values
data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId : Int,
    val selectedDietType : String,
    val selectedDietTypeId : Int
)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    //Creating the Keys
    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    //Creating the DataStore
    private val dataStore: DataStore<Preferences> = context.dataStore


    // Using key, pass to inside function Parameters and
    // Saving those key inside our dataStore
    suspend fun saveMealAndDietType(mealType : String, mealTypeId : Int, dietType : String, dietTypeId : Int){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }


    // When we are reading MealAndDietType, those values from our bottom sheet,
    // we are going to flow to pass this MealAndDietType Class Object
    val readMealAndDietType : Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

}



