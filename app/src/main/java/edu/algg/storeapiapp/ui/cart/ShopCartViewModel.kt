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
        viewModelScope.launch {
            val updatedProducts = _uiState.value.products.map { product ->
                if (product.id == productId) product.copy(quantity = product.quantity + 1) else product
            }
            _uiState.value = _uiState.value.copy(products = updatedProducts)

            // Actualizar la cantidad en la base de datos
            val newQuantity = updatedProducts.find { it.id == productId }?.quantity ?: 0
            productDao.updateProductQuantityInCart(productId, newQuantity)

            calculateTotal()
        }
    }

    // Método para disminuir la cantidad de un producto en el carrito
    fun decreaseProductQuantity(productId: Int) {
        viewModelScope.launch {
            val updatedProducts = _uiState.value.products.toMutableList()
            val productIndex = updatedProducts.indexOfFirst { it.id == productId }
            if (productIndex != -1) {
                val product = updatedProducts[productIndex]
                if (product.quantity > 1) {
                    updatedProducts[productIndex] = product.copy(quantity = product.quantity - 1)
                } else {
                    updatedProducts.removeAt(productIndex)
                    deleteProductFromCart(productId)
                }
                _uiState.value = _uiState.value.copy(products = updatedProducts)

                // Actualizar la cantidad en la base de datos
                val newQuantity = updatedProducts.find { it.id == productId }?.quantity ?: 0
                productDao.updateProductQuantityInCart(productId, newQuantity)

                calculateTotal()
            }
        }
    }

    suspend fun deleteProductFromCart(productId: Int) {
        withContext(Dispatchers.IO) {
            productDao.deleteProductFromCart(productId)
        }
    }

    fun fetchCartName() {
        viewModelScope.launch {
            val cartName = productDao.getCartNameById(CART_ID)
            _uiState.value = _uiState.value.copy(name = cartName)
        }
    }

    fun changeCartName(newName: String) {
        viewModelScope.launch {
            // Actualiza el nombre del carrito en la base de datos
            productDao.updateCartName(CART_ID, newName)

            // Actualiza el estado del UI
            _uiState.value = _uiState.value.copy(name = newName)
        }
    }

    // Función para calcular el total del carrito
    private fun calculateTotal() {
        val total = _uiState.value.products.sumOf { it.price * it.quantity }
        _uiState.value = _uiState.value.copy(totalPrice = total)

        viewModelScope.launch(Dispatchers.IO) {
            productDao.updateCartTotalPrice(CART_ID, total)
        }
    }
}