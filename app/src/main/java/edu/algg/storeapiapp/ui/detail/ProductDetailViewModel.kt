package edu.algg.storeapiapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.algg.storeapiapp.data.db.ProductEntity
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.data.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {
    private val _productUi = MutableLiveData<ProductEntity>()
    val productUi: LiveData<ProductEntity>
        get() = _productUi

    fun fetch(productId: Int) {
        viewModelScope.launch {
            val product = repository.getProduct(productId)
            _productUi.value = product
            //val product = repository.product(product.id) // Asegúrate de tener esta función en tu repositorio
            //_productUi.value = product
        }
    }
}