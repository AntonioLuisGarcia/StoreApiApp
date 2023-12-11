package edu.algg.storeapiapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(): ViewModel() {
    private val repository = ProductRepository
    private val _productUi = MutableLiveData<Product>()
    val pokemonUi: LiveData<Product>
        get() = _productUi

    public fun fetch(name: String) {
        viewModelScope.launch {
            val productApi = repository.fetchById(name)
            _productUi.value = Product(productApi.id,productApi.title,productApi.weight, productApi.height, productApi.front, productApi.image,  productApi.front,  productApi.front)
        }
    }
}