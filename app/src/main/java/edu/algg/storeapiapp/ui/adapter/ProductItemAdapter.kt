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

class ProductItemAdapter(private val context: Context, private val onClick:((View, Product) ->Unit)):ListAdapter<Product, ProductItemAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    interface OnProductClickListener {
        fun onProductClicked(product: Product)
    }

    private var listener: OnProductClickListener? = null

    fun setOnProductClickListener(listener: OnProductClickListener) {
        this.listener = listener
    }

    inner class ProductViewHolder(private val binding: ProductItemLayoutBinding, private val onClick: (View, Product) -> Unit): RecyclerView.ViewHolder(binding.root){
        fun showProduct(product: Product){
            binding.productName.text = product.title
            binding.productPrice.text = product.price.toString()
            binding.productImage.load(product.image)
            binding.itemCard.setOnClickListener {
                onClick(it, product)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.showProduct(getItem(position))
    }

}

object DIFF_CALLBACK: DiffUtil.ItemCallback<Product>(){

    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}
