package edu.algg.storeapiapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(RatingConverter::class)
abstract class ProductDatabase(): RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ProductDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java,
                "product_db"
            ).build()
        }
    }
}