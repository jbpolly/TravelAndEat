package com.mysticraccoon.travelandeat.ui.dashboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mysticraccoon.travelandeat.BR
import com.mysticraccoon.travelandeat.R
import com.mysticraccoon.travelandeat.core.utils.safeNavigate
import com.mysticraccoon.travelandeat.databinding.FragmentDashboardBinding
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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dash_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.clear_saved -> {
            viewModel.clearSavedPlaces()
            true
        }
        R.id.credits -> {
            findNavController().safeNavigate(DashboardFragmentDirections.actionDashboardFragmentToCreditsFragment())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupAdapter() {

        val adapter = SavedPlaceAdapter(SavedPlaceClicked { savedPlace ->
            findNavController().safeNavigate(DashboardFragmentDirections.actionDashboardFragmentToSavedPlaceDetailsFragment(savedPlace = savedPlace, isEdit = true))

        }, SavedPlaceCheckChanged { _, savedPlace ->
            viewModel.updateSavedPlace(savedPlace)
        })

        binding.dashSavedPlacesList.adapter = adapter

        viewModel.savedPlacesList.observe(viewLifecycleOwner){ list->
            list?.let {
                adapter.submitList(it)
            }
        }

    }


    private fun setListeners() {

        binding.dashExploreOption.setOnClickListener {
            findNavController().safeNavigate(DashboardFragmentDirections.actionDashboardFragmentToExploreFragment())

        }

        binding.dashAddPlaceOption.setOnClickListener {
            findNavController().safeNavigate(DashboardFragmentDirections.actionDashboardFragmentToAddEditMealFragment(isEdit = false, savedPlace = null))
        }
    }

}