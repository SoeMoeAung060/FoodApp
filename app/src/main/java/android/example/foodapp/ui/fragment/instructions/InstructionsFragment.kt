package android.example.foodapp.ui.fragment.instructions

import android.example.foodapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.foodapp.adapters.IngredientsAdapter
import android.example.foodapp.databinding.FragmentIngredientsBinding
import android.example.foodapp.databinding.FragmentInstructionsBinding
import android.example.foodapp.util.Constants
import android.example.foodapp.util.Constants.Companion.RECIPES_RESULT
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager


class InstructionsFragment : Fragment() {

    private var _binding : FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        //This args getting our Parcelable data from our pagerAdapter or Detail Activity so we need to that two lines
        val args = arguments
        val myBundle : android.example.foodapp.models.Result? = args?.getParcelable(Constants.RECIPES_RESULT)

        binding.instructionWebView.webViewClient = object : WebViewClient(){}
        binding.instructionWebView.loadUrl(myBundle!!.sourceUrl)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}