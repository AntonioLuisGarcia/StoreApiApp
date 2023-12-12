package edu.algg.storeapiapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.data.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {
    private val _productUi = MutableLiveData<Product>()
    val productUi: LiveData<Product>
        get() = _productUi

    fun fetch(productId: Int) {
        viewModelScope.launch {
            val product = repository.product(productId) // Asegúrate de tener esta función en tu repositorio
            _productUi.value = product
        }
    }
}