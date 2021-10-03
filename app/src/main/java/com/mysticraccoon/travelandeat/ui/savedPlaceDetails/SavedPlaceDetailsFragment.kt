package com.mysticraccoon.travelandeat.ui.savedPlaceDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mysticraccoon.travelandeat.BR
import com.mysticraccoon.travelandeat.core.utils.safeNavigate
import com.mysticraccoon.travelandeat.data.SavedPlace
import com.mysticraccoon.travelandeat.databinding.FragmentSavedPlaceDetailsBinding

class SavedPlaceDetailsFragment: Fragment() {

    private lateinit var binding: FragmentSavedPlaceDetailsBinding
    private val args: SavedPlaceDetailsFragmentArgs by navArgs()
    private var place: SavedPlace? = null
    private var isEdit: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedPlaceDetailsBinding.inflate(inflater, container, false)
        place = args.savedPlace
        isEdit = args.isEdit
        binding.setVariable(BR.url, place?.dishThumb ?: "")
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFields()

        binding.editButton.setOnClickListener {
            findNavController().safeNavigate(SavedPlaceDetailsFragmentDirections.actionSavedPlaceDetailsFragmentToAddEditMealFragment(isEdit = isEdit, savedPlace = place))
        }

    }

    private fun setupFields() {

        binding.editButton.visibility = if(isEdit) View.VISIBLE else View.GONE

        binding.detailsDishName.text = place?.dishName
        binding.detailsDishCategory.text = place?.dishCategory
        binding.detailsLocationText.text = place?.location
        val priceText = "$${place?.dishValue}"
        binding.detailsMealValueText.text = priceText
    }


}