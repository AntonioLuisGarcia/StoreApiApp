package edu.algg.storeapiapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import edu.algg.storeapiapp.databinding.FragmentProductListBinding

import edu.algg.storeapiapp.ui.adapter.ProductItemAdapter


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
        binding.productListId.adapter = adapter
        viewModel.productUi.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

}