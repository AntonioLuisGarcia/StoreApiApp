package edu.algg.storeapiapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.algg.storeapiapp.data.db.ProductDao
import edu.algg.storeapiapp.data.db.ProductDatabase
import javax.inject.Singleton

// Anotación Module para indicar que esta clase provee dependencias.
@Module
// Anotación InstallIn para especificar que las dependencias se instalarán en el componente SingletonComponent.
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // Provee la instancia de ProductDatabase.
    // Anotación Singleton para indicar que la instancia es única en toda la aplicación.
    @Singleton
    @Provides
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        // Retorna una instancia de ProductDatabase usando el contexto de la aplicación.
        return ProductDatabase.getInstance(context)
    }

    // Provee la instancia de ProductDao.
    // Anotación Singleton para indicar que la instancia es única en toda la aplicación.
    @Singleton
    @Provides
    fun providePokemonDao(database: ProductDatabase): ProductDao {
        // Retorna el DAO del producto desde la instancia de la base de datos.
        return database.productDao()
    }

    /*
    *  Cada método anotado con @Provides indica a Dagger cómo proveer una instancia de un tipo específico.
    *  Estos métodos pueden tener parámetros, los cuales también son inyectados por Dagger.
    * */

}