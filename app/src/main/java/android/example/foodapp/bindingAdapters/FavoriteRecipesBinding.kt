package android.example.foodapp.bindingAdapters

import android.example.foodapp.adapters.FavoriteRecipesAdapter
import android.example.foodapp.data.roomDatabase.entity.FavoriteEntity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

class FavoriteRecipesBinding {

    companion object{

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setAndViewVisibility(
            view : View,
            favoritesEntity: List<FavoriteEntity>?,
            mAdapter: FavoriteRecipesAdapter?
            ){
            if (favoritesEntity.isNullOrEmpty()){
                when(view){
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility =View.INVISIBLE
                    }
                }
            }else {
                when(view){
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(favoritesEntity)
                    }
                }
            }
        }
    }
}