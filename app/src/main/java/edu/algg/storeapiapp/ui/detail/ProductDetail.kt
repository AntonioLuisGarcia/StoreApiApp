package edu.algg.storeapiapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.algg.storeapiapp.R
import androidx.lifecycle.Observer
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import edu.algg.storeapiapp.data.db.ProductEntity
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.databinding.FragmentProductDetailBinding

@AndroidEntryPoint
class ProductDetail : Fragment() {
    private val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailArgs by navArgs()
    private lateinit var binding: FragmentProductDetailBinding

    // Variable para mantener la cantidad
    private var quantity: Int = 0

    val observer = Observer<ProductEntity>{
        binding.toolbar.setNavigationOnClickListener(){
            findNavController().popBackStack(R.id.productListFragment, false)
        }
        binding.productImage.load(it.image)
        binding.productName.text = it.title
        binding.productDescription.text = it.description
        binding.productPrice.text = it.price.toString()
        binding.productCategory.text = it.category
        binding.productRate.text = it.rate.toString()
        binding.productCount.text = it.count.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }
        viewModel.fetch((args.productId).toInt())

        viewModel.productUi.observe(viewLifecycleOwner,observer)

        //viewModel.fetch(args.productId)
        //viewModel.pokemonUi.observe(viewLifecycleOwner,observer)
        setupQuantityButtons()
        setupAddButton()
    }

    private fun setupQuantityButtons() {
        binding.buttonIncrease.setOnClickListener {
            quantity++
            updateQuantityDisplay()
        }

        binding.buttonDecrease.setOnClickListener {
            if (quantity > 0) {
                quantity--
                updateQuantityDisplay()
            }
        }
    }

    private fun updateQuantityDisplay() {
        binding.textQuantity.text = quantity.toString()
    }

    private fun setupAddButton() {
        binding.buttonAdd.setOnClickListener {
            viewModel.updateProductQuantity(args.productId.toInt(), quantity)
            findNavController().popBackStack()
        }
    }
}