package edu.algg.storeapiapp.data.api.cart

import edu.algg.storeapiapp.data.db.ProductDao
import edu.algg.storeapiapp.data.db.ShopCartEntity
import edu.algg.storeapiapp.data.repository.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopCartRepository @Inject constructor(private val dao: ProductDao){
    /*
    suspend fun getAllProducts(): List<Product>{
        val charactersFlow = dao.getAll()
        val characterList = mutableListOf<ShopCartModel>()
        charactersFlow.collect { characterEntityList ->
            // Mapear cada CharacterEntity a CharacterModel y agregarlo a la lista
            characterList.addAll(characterEntityList.map {
                it.() })
        }

        return characterList
    }*/
}