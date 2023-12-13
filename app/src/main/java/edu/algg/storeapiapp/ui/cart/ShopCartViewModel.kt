package edu.algg.storeapiapp.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class ShopCartViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {

    private val _uiState = MutableStateFlow(ShopCartUIState(name = "Mi Carrito", products = listOf(), totalPrice = 0.0))
    val uiState: StateFlow<ShopCartUIState>
        get() = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                repository.refreshList()
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message!!)
            }
        }
        viewModelScope.launch {
            repository.product.collect { products ->
                _uiState.value = _uiState.value.copy(products = products)
                calculateTotal()
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
        val updatedProducts = _uiState.value.products.map { product ->
            if (product.id == productId && product.quantity > 1) product.copy(quantity = product.quantity - 1) else product
        }
        _uiState.value = _uiState.value.copy(products = updatedProducts)
        calculateTotal()
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