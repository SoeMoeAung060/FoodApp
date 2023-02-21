package android.example.foodapp.ui.fragment.favorite

import android.app.AlertDialog
import android.example.foodapp.R
import android.example.foodapp.adapters.FavoriteRecipesAdapter
import android.example.foodapp.databinding.FragmentFavoriteRecipesBinding
import android.example.foodapp.viewModels.MainViewModel
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val mainViewModel : MainViewModel by viewModels()
    private val mAdapter : FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }

    private  var _binding : FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter

        //For Delete Icon for Action Bar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favorite_recipes_delete_all_menu, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.deleteAll_favorite_recipe_menu){
                    deleteAll()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupRecyclerView()

//        mainViewModel.readFavoriteRecipe.observe(viewLifecycleOwner) { favoritesEntity ->
//            mAdapter.setData(favoritesEntity)
//        }

        return binding.root
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mainViewModel.deleteAllFavoriteRecipes()
            showSnackBar()
        }
        builder.setNegativeButton("No"){_,_ ->
        }
        builder.setTitle("Delete Everything")
        builder.setMessage("Are you sure, you want to delete Everything")
        builder.create().show()
    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.root,
            "All recipes removed",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    private fun setupRecyclerView(){
        binding.favoriteRecyclerView.adapter = mAdapter
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }
}