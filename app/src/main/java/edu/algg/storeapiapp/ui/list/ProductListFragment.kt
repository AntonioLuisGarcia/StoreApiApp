package edu.algg.storeapiapp.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import edu.algg.storeapiapp.databinding.FragmentProductListBinding

import edu.algg.storeapiapp.ui.adapter.ProductItemAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val viewModel:ProductListViewModel by viewModels()
    private lateinit var binding: FragmentProductListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductItemAdapter()
        val rv = binding.productListId
        rv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch{
           repeatOnLifecycle(Lifecycle.State.STARTED){
               viewModel.uiState.collect{
                   adapter.submitList(it.product)
               }
           }
        }
        /*binding.productListId.adapter = adapter
        viewModel.productUi.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }*/
    }

}