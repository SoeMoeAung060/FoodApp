package android.example.foodapp.ui.fragment.foodjoke

import android.content.Intent
import android.example.foodapp.R
import android.example.foodapp.databinding.FragmentFoodJokeBinding
import android.example.foodapp.databinding.FragmentRecipesBinding
import android.example.foodapp.util.Constants.Companion.API_KEY
import android.example.foodapp.util.NetworkResult
import android.example.foodapp.viewModels.MainViewModel
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {


    /**
     * They are two way initialize viewModel
     *     1.  private val mainViewModel by viewModels<MainViewModel>()
     *     2.  viewModelProvider(requiredActivity()).get(MainViewModel::class.java)
     * **/
    private val mainViewModel by viewModels<MainViewModel>()


    private var _binding : FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    //Default Value of foodJoke for share icon
    private var foodJoke = "No Food Joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.foodJokeTextView.text = response.data?.text
                    //အင်တာနက်ရှိရင် FoodJoke Value ကို အင်တာနက် API ကနေယူမယ်,
                    // အကယ်၍ အင်တာနက်မရှိရင် Local Database ထဲမှာ သိမ်းထားတဲ့ Dataထဲကနေ ယူမယ်
                    if (response.data != null){
                        foodJoke = response.data.text
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodJokeFragment" , "Loading")
                }
            }
        }

        //Share Icon
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.food_joke_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.share_food_joke_menu){
                    val shareIntent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                        this.type = "text/plain"
                    }
                    startActivity(shareIntent)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        return binding.root
    }


    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
                if (!database.isNullOrEmpty()){
                    binding.foodJokeTextView.text = database.first().foodJoke.text
                    // အင်တာနက်မရှိရင် Local Database ထဲမှာ သိမ်းထားတဲ့ Dataထဲကနေ ယူမယ်
                    foodJoke = database[0].foodJoke.text
                }
            }
        }
    }

    // Destroy this Fragment, We want to set this binding to null
    // So we can avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}