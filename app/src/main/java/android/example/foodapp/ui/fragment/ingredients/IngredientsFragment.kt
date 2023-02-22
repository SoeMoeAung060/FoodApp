package android.example.foodapp.ui.fragment.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.foodapp.R
import android.example.foodapp.adapters.IngredientsAdapter
import android.example.foodapp.databinding.FragmentIngredientsBinding
import android.example.foodapp.util.Constants
import androidx.recyclerview.widget.LinearLayoutManager


class IngredientsFragment : Fragment() {


    private val mAdapter : IngredientsAdapter by lazy { IngredientsAdapter() }

    private var _binding : FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        //This args getting our Parcelable data from our pagerAdapter or Detail Activity so we need to that two lines
        val args = arguments
        val myBundle : android.example.foodapp.models.Result? = args?.getParcelable(Constants.RECIPES_RESULT)

        setupRecyclerView()
        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }

        return binding.root
    }

    private fun setupRecyclerView(){
        binding.ingredientRecyclerView.adapter = mAdapter
        binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}