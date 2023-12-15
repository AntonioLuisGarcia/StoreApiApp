package edu.algg.storeapiapp.ui.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.databinding.FragmentShopCartBinding

@AndroidEntryPoint
class ShopCartFragment : Fragment() {

    private lateinit var binding: FragmentShopCartBinding
    private val viewModel: ShopCartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        val adapter = ShopCartAdapter(requireContext(),
            onIncrease = { product -> viewModel.increaseProductQuantity(product.id) },
            onDecrease = { product -> viewModel.decreaseProductQuantity(product.id) }
        )


        // Establecer un LayoutManager
        binding.rvCartItems.layoutManager = LinearLayoutManager(context)

        binding.rvCartItems.adapter = adapter
        viewModel.updateCartProducts()
        viewModel.updateTotalPrice()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitList(it.products)
                }
            }
        }

        binding.btnEditCartName.setOnClickListener {
            val currentCartName = binding.tvCartName.text.toString()
            val action = ShopCartFragmentDirections.actionShopCartFragmentToEditCartNameFragment(currentCartName)
            findNavController().navigate(action)
        }

        binding.btnCheckout.setOnClickListener {
            val shareableText = getShareableText(viewModel.uiState.value.products)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareableText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun getShareableText(products: List<Product>): String {
        return products.joinToString(separator = "\n")  { product ->
            "${product.title} - Cantidad: ${product.quantity}, Precio: ${product.price}"
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    (binding.rvCartItems.adapter as ShopCartAdapter).submitList(uiState.products)

                    // Actualiza el nombre del carrito
                    binding.tvCartName.text = uiState.name

                    uiState.products.forEach { product ->
                        Log.d("Fragment", "Producto: ${product.title}, Cantidad: ${product.quantity}")
                    }
                    binding.tvTotal.text = "Total: \$${uiState.totalPrice}"
                }
            }
        }
    }
}