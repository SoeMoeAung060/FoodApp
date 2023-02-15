package android.example.foodapp.ui.fragment.recipes.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.foodapp.R
import android.example.foodapp.databinding.RecipesBottomSheetBinding
import android.example.foodapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import android.example.foodapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import android.example.foodapp.viewModels.RecipesViewModel
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var _binding : RecipesBottomSheetBinding
    private val binding get() = _binding

    private lateinit var recipesViewModel : RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)


        binding.mealTypeCheapGroup.setOnCheckedStateChangeListener{ group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId.first())
            val selectedMealType = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId.first()
        }

        binding.dietTypeCheapGroup.setOnCheckedStateChangeListener{ group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId.first())
            val selectedDietType = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId.first()
        }


        binding.applyBtn.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)

        }


        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeCheapGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeCheapGroup)

        })

        return binding.root



    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }catch (e: java.lang.Exception){
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

}