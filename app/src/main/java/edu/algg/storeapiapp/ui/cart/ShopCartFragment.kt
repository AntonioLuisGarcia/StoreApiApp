package edu.algg.storeapiapp.ui.cart

import android.content.Context
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
import edu.algg.storeapiapp.R
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.databinding.FragmentShopCartBinding

// Anotación para inyectar dependencias usando Hilt.
@AndroidEntryPoint
class ShopCartFragment : Fragment() {

    // Propiedades para el binding y el ViewModel.
    private lateinit var binding: FragmentShopCartBinding
    private val viewModel: ShopCartViewModel by viewModels()

    // Método que se ejecuta para crear la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método que se ejecuta después de que la vista ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        val adapter = ShopCartAdapter(requireContext(),
            onIncrease = { product -> viewModel.increaseProductQuantity(product.id) },
            onDecrease = { product -> viewModel.decreaseProductQuantity(product.id) }
        )

        binding.rvCartItems.layoutManager = LinearLayoutManager(context)
        binding.rvCartItems.adapter = adapter

        // Solicitudes al ViewModel para actualizar la lista y el precio total.
        viewModel.updateCartProducts()
        viewModel.updateTotalPrice()

        // Observar el estado de la UI y actualizar la lista de productos.
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitList(it.products)
                }
            }
        }

        // Vincula el nombre actual del carrito al ViewModel
        viewModel.fetchCartName()

        // Observar el estado de la UI y actualizar elementos de la UI.
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.tvCartName.text = uiState.name
                    // Actualizar otros elementos de UI si es necesario
                }
            }
        }

        // Boton para editar el nombre del carrito
        binding.btnEditCartName.setOnClickListener {
            val currentCartName = binding.tvCartName.text.toString()
            val action = ShopCartFragmentDirections.actionShopCartFragmentToEditCartNameFragment(currentCartName)
            findNavController().navigate(action)
        }

        // Boton para compartir la lista de productos
        binding.btnCheckout.setOnClickListener {
            // Prepara el texto para compartir el contenido del carrito.
            val cartName = viewModel.uiState.value.name
            val cartTotal = viewModel.uiState.value.totalPrice

            // Formatear el título y subtítulo con la información del carrito
            val title = getString(R.string.cart_name_con_placeholder,cartName)
            val subtitle = getString(R.string.total_price_con_placeholder, cartTotal.toString())
            val productsText = getShareableText(viewModel.uiState.value.products, requireContext())

            // Combinar título, subtítulo y el texto de los productos
            val shareableText = "$title\n$subtitle\n\n$productsText"

            // Crear y lanzar el intent para compartir el contenido.
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareableText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    // Método para generar un texto compartible con los detalles de los productos.
    //Pasamos el contexto para que se pueda traducir al idioma correcto
    private fun getShareableText(products: List<Product>, context: Context): String {
        return products.joinToString(separator = "\n") { product ->
            val formattedQuantity = context.getString(R.string.quantity_con_placeholder, product.quantity.toString())
            val formattedPrice = context.getString(R.string.price_con_placeholder, String.format("%.2f", product.price))
            "${product.title} - $formattedQuantity, $formattedPrice"
        }
    }

    // Método para observar cambios en el ViewModel y actualizar la UI.
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    // Actualiza la lista de productos y el nombre del carrito.
                    (binding.rvCartItems.adapter as ShopCartAdapter).submitList(uiState.products)
                    // Actualiza el nombre del carrito
                    binding.tvCartName.text = uiState.name
                    uiState.products.forEach { product ->
                        Log.d("Fragment", "Producto: ${product.title}, Cantidad: ${product.quantity}")
                    }
                    // Actualiza el total del carrito
                    binding.tvTotal.text = "Total: \$${String.format("%.2f", uiState.totalPrice)}"
                }
            }
        }
    }
}