package android.example.foodapp.ui

import android.example.foodapp.R
import android.example.foodapp.data.roomDatabase.entity.FavoriteEntity
import android.example.foodapp.databinding.ActivityDetailsBinding
import android.example.foodapp.ui.fragment.ingredients.IngredientsFragment
import android.example.foodapp.ui.fragment.instructions.InstructionsFragment
import android.example.foodapp.ui.fragment.overview.OverviewFragment
import android.example.foodapp.util.Constants.Companion.RECIPES_RESULT
import android.example.foodapp.viewModels.MainViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var menuItem : MenuItem

    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())


        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")


        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPES_RESULT, args.result)

        val pagerAdapter = android.example.foodapp.adapters.PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }


    //Favorite Icon for Action Bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        //Favorite Button ကို နှိပ်လိုက်ရင် အဖြူရောင်ကနေအဝါရောင်ပြောင်းမယ် နောက်တစ်ချက်ပြန်နှိပ်ရင် နဂိုမူရင်းပုံစံအတိုင်းပြန်ပြောင်းမယ်...
        menuItem = menu!!.findItem(R.id.save_to_favorite_menu)
        checkSavedRecipes(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorite_menu && !recipeSaved) {
            saveToFavorites(item)
        } else if ( item.itemId == R.id.save_to_favorite_menu && recipeSaved){
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipe.observe(this) { favoritesEntity ->
            try {
                for (saveRecipes in favoritesEntity){
                    if (saveRecipes.result.recipeId == args.result.recipeId){
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = saveRecipes.id
                        recipeSaved = true
                    }else{
                        changeMenuItemColor(menuItem, R.color.white)

                    }
                }
            }catch (e : Exception){
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }


    //For Save Favorite Icon from Detail Activity
    private fun saveToFavorites(item: MenuItem) {
        val favoriteEntity =
            FavoriteEntity(
                0,
                args.result // result of selected recipe through from our args
            )
        mainViewModel.insertFavoriteRecipes(favoriteEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe Saved")
        recipeSaved = true
    }

    //For Remove Favorite Icon from Detail Activity
    private fun removeFromFavorites(item: MenuItem){
        val favoriteEntity =
            FavoriteEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteFavoriteRecipes(favoriteEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }
}