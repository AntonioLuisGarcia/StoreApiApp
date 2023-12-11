package edu.algg.storeapiapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.algg.storeapiapp.data.api.Product
import edu.algg.storeapiapp.data.api.ProductApiModel
import edu.algg.storeapiapp.data.api.ProductListApiModel
import edu.algg.storeapiapp.data.api.ProductRepository
import kotlinx.coroutines.launch

class ProductListViewModel(): ViewModel() {
    private val repository =  ProductRepository.getInstance()
    private val _productUi = MutableLiveData<List<Product>>()
    val productUi: LiveData<List<Product>>
        get() = _productUi

    private val observer = Observer<List<Product>> {
        _productUi.value = it
    }

    init{
        fetch()
    }

    fun fetch(){
        repository.product.observeForever(observer)
        viewModelScope.launch {
            repository.fetchAll()
        }
    }

    override fun onCleared(){
        super.onCleared()
        repository.product.removeObserver(observer)
    }
}