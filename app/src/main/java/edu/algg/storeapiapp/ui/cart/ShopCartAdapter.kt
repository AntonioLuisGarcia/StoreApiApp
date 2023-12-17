package edu.algg.storeapiapp.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import edu.algg.storeapiapp.R
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.databinding.ProductCartItemBinding

// Clase adaptador para elementos de carrito de compras en un RecyclerView.
class ShopCartAdapter (private val context: Context, private val onIncrease: (Product) -> Unit, private val onDecrease: (Product) -> Unit) : ListAdapter<Product, ShopCartAdapter.ShopCartViewHolder>(ProductDiffCallback){

    // Clase interna ViewHolder para gestionar la visualización de un producto en el carrito.
    inner class ShopCartViewHolder(private val binding: ProductCartItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            // Si el producto no es nulo, realiza la configuración del elemento.
            if (product != null) {
                val context = binding.root.context // Obtener el contexto de la vista
                val formattedPrice = String.format("%.2f", product.price) // Formatea el precio a dos decimales
                binding.tvProductName.text = product.title
                binding.imgProduct.load(product.image)
                binding.tvQuantity.text = product.quantity.toString()
                binding.tvProductPrice.text = context.getString(R.string.price_con_placeholder, formattedPrice)
                binding.btnIncrease.setOnClickListener { onIncrease(product) }
                binding.btnDecrease.setOnClickListener { onDecrease(product) }
            }
        }
    }

    // Objeto para comparar elementos en la lista y detectar cambios.
    private object ProductDiffCallback: DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem

    }

    // Método para crear un ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCartViewHolder {
        val binding = ProductCartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ShopCartViewHolder(binding)
    }

    // Método para vincular un producto a un ViewHolder.
    override fun onBindViewHolder(holder: ShopCartViewHolder, position: Int) = holder.bind(getItem(position))
}