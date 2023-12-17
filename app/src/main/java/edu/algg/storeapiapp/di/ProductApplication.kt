package edu.algg.storeapiapp.di

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.HiltAndroidApp

// Anotación HiltAndroidApp para indicar que esta clase es la aplicación principal
// y será utilizada por Hilt para la inyección de dependencias.
@HiltAndroidApp
class ProductApplication:Application(), ImageLoaderFactory {
    // Sobrescribe el método para proveer un cargador de imágenes personalizado usando Coil.
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            // Configura la caché de memoria para las imágenes.
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25) // Establece el tamaño máximo de la caché de memoria.
                    .build()
            }
            // Configura la caché de disco para las imágenes.
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache")) // Define el directorio de la caché de disco.
                    .maxSizePercent(0.1) // Establece el tamaño máximo de la caché de disco.
                    .build()
            }
            .build()
    }
}