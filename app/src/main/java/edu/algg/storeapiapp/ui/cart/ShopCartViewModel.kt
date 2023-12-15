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


@HiltViewModel
class ShopCartViewModel @Inject constructor(private val repository: ProductRepository, private val productDao: ProductDao): ViewModel() {

    private val _uiState = MutableStateFlow(ShopCartUIState(name = "Mi Carrito", products = listOf(), totalPrice = 0.0))
    val uiState: StateFlow<ShopCartUIState>
        get() = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                // Crear un carrito si no existe
                createCartIfNotExists()
                repository.refreshList()
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message!!)
            }

        }
    }


    private suspend fun createCartIfNotExists() {
        val existingCart = productDao.getCartById(CART_ID) // CART_ID es el ID del carrito
        if (existingCart == null) {
            val newCart = ShopCartEntity(id = CART_ID, name = "Mi Carrito", totalPrice = 0.0)
            productDao.insertCart(newCart)
        }
    }

    companion object {
        const val CART_ID = 1 // ID constante para el único carrito
    }

    fun updateTotalPrice() {
        viewModelScope.launch {
            // Si 'getTotalPriceInCart()' es 'null', se utilizará '0.0' como valor por defecto.
            val totalPrice = productDao?.getTotalPriceInCart() ?: 0.0
            _uiState.value = _uiState.value.copy(totalPrice = totalPrice)
            Log.d("ShopCartFragment", "Carrito actualizado con ${totalPrice} dolares.")
        }
    }

    fun updateCartProducts() {
        viewModelScope.launch {
            repository.productsInCart.collect { products ->
                _uiState.value = _uiState.value.copy(products = products)
                calculateTotal()
                Log.d("ShopCartFragment", "Carrito actualizado con ${products.size} productos.")
            }
        }
    }

    fun increaseProductQuantity(productId: Int) {
        val updatedProducts = _uiState.value.products.map { product ->
            if (product.id == productId) product.copy(quantity = product.quantity + 1) else product
        }
        _uiState.value = _uiState.value.copy(products = updatedProducts)
        calculateTotal()
    }

    // Método para disminuir la cantidad de un producto en el carrito
    fun decreaseProductQuantity(productId: Int) {
        viewModelScope.launch {
            // Obtén una lista mutable para poder modificarla
            val updatedProducts = _uiState.value.products.toMutableList()
            val productIndex = updatedProducts.indexOfFirst { it.id == productId }
            if (productIndex != -1) {
                val product = updatedProducts[productIndex]
                if (product.quantity > 1) {
                    // Si la cantidad es mayor que 1, disminuye la cantidad
                    updatedProducts[productIndex] = product.copy(quantity = product.quantity - 1)
                } else {
                    // Si la cantidad llega a 0, elimina el producto de la lista
                    updatedProducts.removeAt(productIndex)
                    // Además, actualiza la base de datos para eliminar el producto del carrito
                    deleteProductFromCart(productId)
                }
                // Actualiza el estado del UI con la lista modificada y recalcula el total
                _uiState.value = _uiState.value.copy(products = updatedProducts)
                calculateTotal()
            }
        }
    }

    suspend fun deleteProductFromCart(productId: Int) {
        withContext(Dispatchers.IO) {
            productDao.deleteProductFromCart(productId)
        }
    }


    fun changeCartName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    // Función para calcular el total del carrito
    private fun calculateTotal() {
        val total = _uiState.value.products.sumOf { it.price * it.quantity }
        _uiState.value = _uiState.value.copy(totalPrice = total)
    }
}