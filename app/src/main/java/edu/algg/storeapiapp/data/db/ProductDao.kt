package edu.algg.storeapiapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listProductEntity: List<ProductEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listProductEntity: ProductEntity)
    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<ProductEntity>>
}