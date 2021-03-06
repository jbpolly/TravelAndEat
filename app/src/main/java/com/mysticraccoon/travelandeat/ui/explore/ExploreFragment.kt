package com.mysticraccoon.travelandeat.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mysticraccoon.travelandeat.BR
import com.mysticraccoon.travelandeat.core.utils.safeNavigate
import com.mysticraccoon.travelandeat.databinding.FragmentExploreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment: Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private val viewModel: ExploreViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExploreBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner

        val manager = GridLayoutManager(requireActivity(), 2)
        binding.exploreFoodCategoriesList.layoutManager = manager
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListeners()

    }

    private fun setupAdapter() {

        val adapter = ExploreCategoriesAdapter(FoodCategoryClicked {
           findNavController().safeNavigate(ExploreFragmentDirections.actionExploreFragmentToFoodFromCategoryFragment(it.title))
        })

        binding.exploreFoodCategoriesList.adapter = adapter

        viewModel.foodCategories.observe(viewLifecycleOwner){ list ->
            list?.let {
                adapter.submitList(it)
            }
        }

    }

    private fun setupListeners() {

        viewModel.showSnackBar.observe(viewLifecycleOwner){ text ->
            Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT).show()
        }

    }



}