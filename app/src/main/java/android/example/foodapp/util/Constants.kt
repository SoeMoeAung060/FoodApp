package android.example.foodapp.util

class Constants {

    companion object{

        const val BASE_URL = "https://api.spoonacular.com"
        const val INGREDIENTS_BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val API_KEY = "0c218c0d76014232bbd3c1f137e4395a"

        const val RECIPES_RESULT = "recipeBundle"

        //API Query Keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIEPE_NUTRITION = "addRecipeNutrition"
        const val QUERY_ADD_RECIEPE_INFORMATION = "addReciepeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"
        const val QUERY_MAX_READY_TIME = "maxReadyTime"


        //ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"


        //Bottom Sheet and Preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val PREFERENCES_NAME = "foody_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeID"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeID"
        const val PREFERENCES_BACK_ONLINE = "backOnline"




    }
}