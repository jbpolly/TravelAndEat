package com.mysticraccoon.travelandeat.ui.selectMeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mysticraccoon.travelandeat.BR
import com.mysticraccoon.travelandeat.databinding.FragmentSelectMealBinding
import com.mysticraccoon.travelandeat.ui.addEditMeal.AddEditMealViewModel
import com.mysticraccoon.travelandeat.ui.foodFromCategory.FoodItemClicked
import com.mysticraccoon.travelandeat.ui.foodFromCategory.FoodItemFromCategoryAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectMealFragment: Fragment() {

    private lateinit var binding: FragmentSelectMealBinding
    private val viewModel: AddEditMealViewModel by sharedViewModel()
    var adapter: FoodItemFromCategoryAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectMealBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupListeners()
    }

    private fun setupAdapter() {

        adapter = FoodItemFromCategoryAdapter(FoodItemClicked {
            viewModel.setMeal(it)
            findNavController().popBackStack()
        })
        binding.searchList.adapter = adapter
        viewModel.searchFoodList.observe(viewLifecycleOwner){ list->
            list?.let {
                adapter?.submitList(it)
            }
        }
    }

    private fun setupListeners() {

        viewModel.showSnackBar.observe(viewLifecycleOwner){ text ->
            Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.cleanSearch.observe(viewLifecycleOwner){ clean ->
            if(clean){
                adapter?.submitList(listOf())
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.searchField.addTextChangedListener(viewModel.searchDebounceWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.searchField.removeTextChangedListener(viewModel.searchDebounceWatcher)
    }

}