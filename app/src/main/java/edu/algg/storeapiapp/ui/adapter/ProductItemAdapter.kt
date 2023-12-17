package edu.algg.storeapiapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.databinding.ProductItemLayoutBinding

// Clase adaptador para elementos de producto en un RecyclerView.
class ProductItemAdapter(private val context: Context, private val onClick:((View, Product) ->Unit)):ListAdapter<Product, ProductItemAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    // Clase interna ViewHolder para gestionar la visualización de un producto.
    inner class ProductViewHolder(private val binding: ProductItemLayoutBinding, private val onClick: (View, Product) -> Unit): RecyclerView.ViewHolder(binding.root){
        // Método para mostrar los detalles del producto en el elemento.
        fun showProduct(product: Product){
            binding.productName.text = product.title // Muestra el título del producto.
            binding.productPrice.text = product.price.toString() // Muestra el precio del producto.
            binding.productImage.load(product.image) // Carga la imagen del producto usando Coil.
            binding.itemCard.setOnClickListener {
                onClick(it, product) // Maneja el evento de clic en el elemento.
            }
        }
    }

    // Método para crear un ViewHolder.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onClick)
    }

    // Método para vincular un producto a un ViewHolder.
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.showProduct(getItem(position))
    }

}

// Objeto para comparar elementos en la lista y detectar cambios.
object DIFF_CALLBACK: DiffUtil.ItemCallback<Product>(){

    // Comprueba si dos elementos son el mismo.
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

    // Comprueba si el contenido de dos elementos es el mismo.
    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}
