package edu.algg.storeapiapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.algg.storeapiapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState(listOf()))
    val uiState: StateFlow<ProductListUiState>
        get() = _uiState.asStateFlow()

    /*private val observer = Observer<List<Product>> {
        _uiState.value = it
    }*/

    /*init{
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
    }*/

    init{

        viewModelScope.launch {
            try{
                repository.refreshList()
            }
            catch (e:IOException){
                _uiState.value = _uiState.value.copy(errorMessage = e.message!!)
            }
        }

        viewModelScope.launch {
            repository.product.collect{
                _uiState.value = ProductListUiState(it)
            }
        }
    }
}