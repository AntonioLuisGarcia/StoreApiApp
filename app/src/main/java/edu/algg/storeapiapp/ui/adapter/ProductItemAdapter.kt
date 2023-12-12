package edu.algg.storeapiapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import edu.algg.storeapiapp.data.repository.Product
import edu.algg.storeapiapp.databinding.ProductItemLayoutBinding

class ProductItemAdapter():ListAdapter<Product, ProductItemAdapter.ProductViewHolder>(DIFF_CALLBACK) {


    interface OnProductClickListener {
        fun onProductClicked(product: Product)
    }

    private var listener: OnProductClickListener? = null

    fun setOnProductClickListener(listener: OnProductClickListener) {
        this.listener = listener
    }

    inner class ProductViewHolder(private val binding: ProductItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun showProduct(product: Product){
            binding.productName.text = product.title
            binding.productPrice.text = product.price.toString()
            binding.productImage.load(product.image)

            itemView.setOnClickListener {
                onProductClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.showProduct(product)

        holder.itemView.setOnClickListener {
            listener?.onProductClicked(product)
        }
    }

    val adapter = ProductItemAdapter().apply {
        setOnProductClickListener(object : ProductItemAdapter.OnProductClickListener {
            override fun onProductClicked(product: Product) {
                // Lógica de click aquí
            }
        })
    }

}

object DIFF_CALLBACK: DiffUtil.ItemCallback<Product>(){

    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}
