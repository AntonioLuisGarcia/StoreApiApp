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
        binding.productPrice.text = getString(R.string.price_con_placeholder,it.price.toString())
        binding.productCategory.text = getString(R.string.categoria_con_placeholder,it.category)
        binding.ratingBar.rating = it.rate.toFloat()
        binding.productCount.text = getString(R.string.count_con_placeholder,it.count.toString())
        // Actualizar la cantidad con la cantidad del producto
        quantity = it.quantity
        updateQuantityDisplay()
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

        // Establece la cantidad inicial cuando se cargan los detalles del producto

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

            // Vincular el producto al carrito (suponiendo que ya tienes un carrito con un id específico)
            val cartId = 1 // El ID del carrito al que quieres asignar el producto
            viewModel.assignProductToCart(args.productId.toInt(), cartId)

            viewModel.productUi.observe(viewLifecycleOwner) { productEntity ->
                // Establece la cantidad inicial cuando se cargan los detalles del producto
                quantity = productEntity.quantity
                updateQuantityDisplay()
            }

            findNavController().popBackStack()
        }
    }
}

/*
*
* class ProductDetail : Fragment() {
    private val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailArgs by navArgs()
    private lateinit var binding: FragmentProductDetailBinding

    // Variable para mantener la cantidad
    private var quantity: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.fetch(args.productId.toInt())

        viewModel.productUi.observe(viewLifecycleOwner) { productEntity ->
            // Actualiza la UI con los detalles del producto
            updateProductDetails(productEntity)

            // Configura los botones una vez que se tienen los detalles del producto
            setupQuantityButtons()
            setupAddButton()
        }
    }

    private fun updateProductDetails(productEntity: ProductEntity) {
        binding.productImage.load(productEntity.image)
        binding.productName.text = productEntity.title
        binding.productDescription.text = productEntity.description
        binding.productPrice.text = productEntity.price.toString()
        binding.productCategory.text = productEntity.category
        binding.productRate.text = productEntity.rate.toString()
        binding.productCount.text = productEntity.count.toString()

        // Establece la cantidad inicial basada en el producto
        quantity = productEntity.quantity
        updateQuantityDisplay()
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
            // Actualiza la cantidad en el ViewModel
            viewModel.updateProductQuantity(args.productId.toInt(), quantity)

            // Navega hacia atrás después de actualizar
            findNavController().popBackStack()
        }
    }
}

* */