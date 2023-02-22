package android.example.foodapp.ui.fragment.overview

import android.example.foodapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.foodapp.databinding.FragmentOverviewBinding
import android.example.foodapp.util.Constants.Companion.RECIPES_RESULT
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import org.jsoup.Jsoup
import org.w3c.dom.Text


class OverviewFragment : Fragment() {

    private var _binding : FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        //This args getting our Parcelable data from our pagerAdapter or Detail Activity so we need to that two lines
        val args = arguments
        val myBundle : android.example.foodapp.models.Result = args?.getParcelable<android.example.foodapp.models.Result>(RECIPES_RESULT) as android.example.foodapp.models.Result

        binding.mainImageView.load(myBundle.image)
        binding.timeTextView.text = myBundle.readyInMinutes.toString()
        binding.heartTextView.text = myBundle.aggregateLikes.toString()
        binding.titleTextView.text = myBundle.title
        myBundle.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.summaryTextView.text = summary
        }


        updateColors(myBundle.vegan, binding.veganTextView, binding.veganImageView)
        updateColors(myBundle.vegetarian, binding.vegetableTextView, binding.vegetableImageView)
        updateColors(myBundle.dairyFree, binding.dairyFreeTextView, binding.dairyFreeImageView)
        updateColors(myBundle.glutenFree, binding.glutenFreeTextView, binding.glutenFreeImageView)
        updateColors(myBundle.veryHealthy, binding.healthyTextView, binding.healthyImageView)
        updateColors(myBundle.cheap, binding.cheapTextView, binding.cheapImageView)



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateColors(stateIsOn : Boolean, textView : TextView, imageView: ImageView){
        if (stateIsOn){
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }




}