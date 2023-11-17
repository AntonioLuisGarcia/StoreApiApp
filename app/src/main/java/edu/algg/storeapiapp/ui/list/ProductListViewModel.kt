package edu.algg.storeapiapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.algg.storeapiapp.data.api.ProductApiModel
import edu.algg.storeapiapp.data.api.ProductListApiModel
import edu.algg.storeapiapp.data.api.ProductRepository
import kotlinx.coroutines.launch

class ProductListViewModel(): ViewModel() {
    private val repository =  ProductRepository.getInstance()
    private val _productUi = MutableLiveData<List<ProductApiModel>>()
    val productUi: LiveData<List<ProductApiModel>>
        get() = _productUi

    private val observer = Observer<ProductListApiModel>{
        _productUi.value = it.products.map{
                ProductApiModel(it.id,
                    it.title,
                    it.price,
                    it.description,
                    it.category,
                    it.image,
                    it.rating
                )
        }
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