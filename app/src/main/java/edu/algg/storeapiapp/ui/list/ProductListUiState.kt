package edu.algg.storeapiapp.ui.list

import edu.algg.storeapiapp.data.repository.Product


data class ProductListUiState(
    val product: List<Product>,
    val errorMessage: String?=null,
)
