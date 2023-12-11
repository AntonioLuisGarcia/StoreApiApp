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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return ProductDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun providePokemonDao(database: ProductDatabase): ProductDao {
        return database.productDao()
    }

}