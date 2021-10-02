package com.mysticraccoon.travelandeat.ui.foodFromCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mysticraccoon.travelandeat.BR
import com.mysticraccoon.travelandeat.databinding.FragmentMealFromCategoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodFromCategoryFragment: Fragment() {

    private lateinit var binding: FragmentMealFromCategoryBinding
    private val viewModel: FoodFromCategoryViewModel by viewModel()
    private val foodFromCategoryArgs by navArgs<FoodFromCategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealFromCategoryBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupListeners()
        viewModel.setCategory(foodFromCategoryArgs.category)

    }

    private fun setupAdapter() {

        val adapter = FoodItemFromCategoryAdapter(FoodItemClicked {
            //todo item selected?
        })

        binding.categoryFoodList.adapter = adapter

        viewModel.foodList.observe(viewLifecycleOwner){ list->
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