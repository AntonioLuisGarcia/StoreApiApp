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
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.databinding.FragmentProductDetailBinding


class ProductDetail : Fragment() {
    private val args: ProductDetailArgs by navArgs()
    private val viewModel: ProductDetailViewModel by viewModels()
    private lateinit var binding: FragmentProductDetailBinding

    val observer = Observer<Product>{
        binding.topAppBar.setNavigationOnClickListener(){
            findNavController().popBackStack(R.id.productListFragment, false)
        }
        binding.productImage.load(it.image)
        binding.productName.text = it.title
        binding.productDescription.text = it.description
        binding.productPrice.text = it.price.toString()
        binding.productCategory.text = it.category
        //añadir rate y count
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
        viewModel.fetch(args.p)
        viewModel.pokemonUi.observe(viewLifecycleOwner,observer)
    }
}