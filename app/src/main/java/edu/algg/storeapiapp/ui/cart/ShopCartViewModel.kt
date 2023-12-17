package edu.algg.storeapiapp.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.algg.storeapiapp.data.db.ProductDao
import edu.algg.storeapiapp.data.db.ShopCartEntity
import edu.algg.storeapiapp.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

// Anotación HiltViewModel para permitir la inyección de dependencias en el ViewModel.
@HiltViewModel
class ShopCartViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {

    // Estado interno mutable del ViewModel.
    private val _uiState = MutableStateFlow(ShopCartUIState(name = "Mi Carrito", products = listOf(), totalPrice = 0.0))

    // Estado expuesto inmutable para ser observado por la UI.
    val uiState: StateFlow<ShopCartUIState>
        get() = _uiState.asStateFlow()


    init {
        // Inicialización del ViewModel.
        viewModelScope.launch {
            try {
                // Crea un carrito si no existe y actualiza la lista de productos.
                createCartIfNotExists()
                repository.refreshList()
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message!!)
            }
        }
    }

    // Función para crear un carrito si no existe.
    private suspend fun createCartIfNotExists() {
        viewModelScope.launch {
            repository.createCartIfNotExists(CART_ID, "Mi Carrito")
        }
    }

    // Constante para el ID del carrito.
    companion object {
        const val CART_ID = 1 // ID constante para el único carrito
    }

    // Función para actualizar el precio total del carrito.
    fun updateTotalPrice() {
        viewModelScope.launch {
            val totalPrice = repository.getTotalPriceInCart() ?: 0.0
            _uiState.value = _uiState.value.copy(totalPrice = totalPrice)
        }
    }

    // Función para actualizar los productos en el carrito.
    fun updateCartProducts() {
        viewModelScope.launch {
            repository.productsInCart.collect { products ->
                _uiState.value = _uiState.value.copy(products = products)
                calculateTotal()
                Log.d("ShopCartFragment", "Carrito actualizado con ${products.size} productos.")
            }
        }
    }

    // Función para incrementar la cantidad de un producto.
    fun increaseProductQuantity(productId: Int) {
        viewModelScope.launch {
            repository.increaseProductQuantity(productId)
            updateCartProducts()
        }
    }

    // Método para disminuir la cantidad de un producto en el carrito
    fun decreaseProductQuantity(productId: Int) {
        viewModelScope.launch {
            repository.decreaseProductQuantity(productId)
            updateCartProducts()
        }
    }


    // Función para obtener el nombre del carrito.
    fun fetchCartName() {
        viewModelScope.launch {
            val cartName = repository.getCartName(ShopCartViewModel.CART_ID)
            _uiState.value = _uiState.value.copy(name = cartName)
        }
    }

    // Función para cambiar el nombre del carrito.
    fun changeCartName(newName: String) {
        viewModelScope.launch {
            repository.changeCartName(ShopCartViewModel.CART_ID, newName)
            fetchCartName()
        }
    }

    // Función para calcular el total del carrito
    private fun calculateTotal() {
        viewModelScope.launch {
            val total = repository.calculateTotalPrice()
            _uiState.value = _uiState.value.copy(totalPrice = total)
        }
    }
}