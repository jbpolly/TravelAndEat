package com.mysticraccoon.travelandeat.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mysticraccoon.travelandeat.BR
import com.mysticraccoon.travelandeat.core.utils.safeNavigate
import com.mysticraccoon.travelandeat.databinding.FragmentDashboardBinding
import com.mysticraccoon.travelandeat.ui.foodFromCategory.FoodItemClicked
import com.mysticraccoon.travelandeat.ui.foodFromCategory.FoodItemFromCategoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment: Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setListeners()
    }

    private fun setupAdapter() {

        val adapter = SavedPlaceAdapter(SavedPlaceClicked { savedPlace ->
            //todo item selected?

        }, SavedPlaceCheckChanged { _, savedPlace ->
            viewModel.updateSavedPlace(savedPlace)
        })

        binding.dashSavedPlacesList.adapter = adapter

//        viewModel..observe(viewLifecycleOwner){ list->
//            list?.let {
//                adapter.submitList(it)
//            }
//        }

    }


    private fun setListeners() {

        binding.dashExploreOption.setOnClickListener {
            findNavController().safeNavigate(DashboardFragmentDirections.actionDashboardFragmentToExploreFragment())

        }

        binding.dashAddPlaceOption.setOnClickListener {
            findNavController().safeNavigate(DashboardFragmentDirections.actionDashboardFragmentToAddEditMealFragment(isEdit = false, savedPlace = null))
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }


}