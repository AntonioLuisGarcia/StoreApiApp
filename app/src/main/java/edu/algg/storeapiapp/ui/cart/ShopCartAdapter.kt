package edu.algg.storeapiapp.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.data.repository.ShopCart
import edu.algg.storeapiapp.databinding.ProductCartItemBinding

class ShopCartAdapter (private val context: Context, private val onIncrease: (Product) -> Unit, private val onDecrease: (Product) -> Unit) : ListAdapter<Product, ShopCartAdapter.ShopCartViewHolder>(ProductDiffCallback){
                                                ///mirar tipo/////////
    inner class ShopCartViewHolder(private val binding: ProductCartItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.title
            binding.tvQuantity.text = product.price.toString()
            binding.btnIncrease.setOnClickListener { onIncrease(product) }
            binding.btnDecrease.setOnClickListener { onDecrease(product) }
        }
    }

    private object ProductDiffCallback: DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCartViewHolder {
        val binding = ProductCartItemBinding.inflate( // verificar si es este xml
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ShopCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopCartViewHolder, position: Int) = holder.bind(getItem(position))
}