package android.example.foodapp.bindingAdapters

import android.example.foodapp.data.roomDatabase.entity.FoodJokeEntity
import android.example.foodapp.models.FoodJoke
import android.example.foodapp.util.NetworkResult
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {

    companion object {


        @BindingAdapter("readApiResponse3", "readDatabase3", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            dataBase: List<FoodJokeEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (dataBase != null){
                                if (dataBase.isEmpty()){
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when(view){
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
                else -> {}
            }
        }


        @BindingAdapter("readApiResponse4", "readDatabase4", requireAll = true)
        @JvmStatic
        fun setErrorViewsVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            dataBase: List<FoodJokeEntity>?
        ){
            if (dataBase != null){
                if (dataBase.isEmpty()){
                    view.visibility = View.VISIBLE
                    if (view is TextView){
                        if (apiResponse != null){
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }
            if (apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }
        }
    }
}