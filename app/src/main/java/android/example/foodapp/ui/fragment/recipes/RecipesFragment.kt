package android.example.foodapp.ui.fragment.recipes

import android.example.foodapp.MainViewModel
import android.example.foodapp.adapters.RecipesAdapter
import android.example.foodapp.databinding.FragmentRecipesBinding
import android.example.foodapp.util.NetworkResult
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var _binding: FragmentRecipesBinding
    private val binding get() = _binding

    private val mAdapter by lazy {
        RecipesAdapter()
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        setupRecyclerView()
        requestApiData()

        return binding.root
    }

    //request API data
    private fun requestApiData(){
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is NetworkResult.Success ->{
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error ->{
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }

    private fun applyQueries() : HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()

        queries["number"] = "50"
        queries["apiKey"] = "API_KEY"
        queries["type"] = "drink"
        queries["diet"] = "vegan"
        queries["addReciepeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    //setup RecyclerView Adapter
    private fun setupRecyclerView(){
        _binding.recyclerView.adapter = mAdapter
        _binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        _binding.shimmerViewContainer.startShimmer()
    }

    private fun hideShimmerEffect(){
        _binding.shimmerViewContainer.hideShimmer()
    }


}