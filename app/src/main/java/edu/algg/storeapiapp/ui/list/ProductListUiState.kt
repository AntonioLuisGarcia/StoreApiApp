package edu.algg.storeapiapp.ui.list

import edu.algg.storeapiapp.data.api.Product

data class ProductListUiState(
    val product: List<edu.algg.storeapiapp.data.repository.Product>,
    val errorMessage: String?=null,
)
