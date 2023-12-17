package edu.algg.storeapiapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Anotación para definir una base de datos de Room. Especifica las entidades y la versión de la base de datos.
@Database(entities = [ProductEntity::class, ShopCartEntity::class], version = 1)
abstract class ProductDatabase(): RoomDatabase() {

    // Método abstracto para obtener el DAO del producto.
    abstract fun productDao(): ProductDao

    // Aqui guardamos metodos estaticos  y propiedades estaticas de la clase, y es singleton
    companion object {
        @Volatile // Marcador para asegurar que la variable INSTANCE se mantiene actualizada y visible para todos los hilos.
        private var INSTANCE: ProductDatabase? = null

        // Método estático para obtener una instancia de la base de datos.
        fun getInstance(context: Context): ProductDatabase {
            // Retorna la instancia existente o crea una nueva si es necesario, de forma segura entre hilos.
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    db-> INSTANCE =db }
            }
        }

        // Método privado para construir la base de datos.
        private fun buildDatabase(context: Context): ProductDatabase {
            // Crea y retorna una nueva instancia de la base de datos usando el constructor de Room.
            return Room.databaseBuilder(
                context.applicationContext, // Contexto de la aplicación.
                ProductDatabase::class.java, // Clase de la base de datos.
                "product_db" // Nombre de la base de datos.
            ).build() // Construye la base de datos.
        }
    }
}