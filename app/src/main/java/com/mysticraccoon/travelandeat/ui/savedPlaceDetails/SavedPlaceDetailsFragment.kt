package com.mysticraccoon.travelandeat.ui.savedPlaceDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mysticraccoon.travelandeat.BR
import com.mysticraccoon.travelandeat.data.SavedPlace
import com.mysticraccoon.travelandeat.databinding.FragmentSavedPlaceDetailsBinding

class SavedPlaceDetailsFragment: Fragment() {

    private lateinit var binding: FragmentSavedPlaceDetailsBinding
    private val args: SavedPlaceDetailsFragmentArgs by navArgs()
    private var place: SavedPlace? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedPlaceDetailsBinding.inflate(inflater, container, false)
        place = args.savedPlace
        binding.setVariable(BR.url, place?.dishThumb ?: "")
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFields()
    }

    private fun setupFields() {

        binding.detailsDishName.text = place?.dishName
        binding.detailsDishCategory.text = place?.dishCategory
        binding.detailsLocationText.text = place?.location
        val priceText = "$${place?.dishValue}"
        binding.detailsMealValueText.text = priceText
    }


}