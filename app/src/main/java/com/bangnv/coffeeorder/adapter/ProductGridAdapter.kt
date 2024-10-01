package com.bangnv.coffeeorder.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangnv.coffeeorder.R
import com.bangnv.coffeeorder.constant.Constant
import com.bangnv.coffeeorder.constant.GlobalFunction.formatNumberWithPeriods
import com.bangnv.coffeeorder.databinding.ItemProductGridHomeBinding
import com.bangnv.coffeeorder.model.Product
import com.bangnv.coffeeorder.utils.GlideUtils.loadUrl300

class ProductGridAdapter(
    private val mContext: Context,
    private var products: List<Product>,
    private val iOnClickProductItemListener: IOnClickProductItemListener
) : RecyclerView.Adapter<ProductGridAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductGridHomeBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Product>) {
        Log.d("ProductGridAdapter", "Products updated: $newList")
        products = newList
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(private val binding: ItemProductGridHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {

            loadUrl300(product.image, binding.imgProduct)

            if (product.sale <= 0) {
                binding.tvSaleOff.visibility = View.GONE
                binding.tvPrice.visibility = View.GONE
                val strPrice: String = formatNumberWithPeriods(product.price) + Constant.CURRENCY
                binding.tvPriceSale.text = strPrice
            } else {
                binding.tvSaleOff.visibility = View.VISIBLE
                binding.tvPrice.visibility = View.VISIBLE
                val strSale =
                    mContext.getString(R.string.reduce) + " " + product.sale + mContext.getString(R.string.percent)
                binding.tvSaleOff.text = strSale
                val strOldPrice: String = formatNumberWithPeriods(product.price) + Constant.CURRENCY
                binding.tvPrice.text = strOldPrice
                binding.tvPrice.paintFlags = binding.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                val realPrice: Int = (product.price * (1 - (product.sale / 100.0))).toInt()
                val stringBuilder = StringBuilder()
                stringBuilder.append(formatNumberWithPeriods(realPrice))
                stringBuilder.append(Constant.CURRENCY)
                val strRealPrice: String = stringBuilder.toString()
                binding.tvPriceSale.text = strRealPrice
            }
            binding.tvProductName.text = product.name
            binding.layoutItem.setOnClickListener {
                iOnClickProductItemListener.onClickItemProduct(product)
            }

            binding.executePendingBindings() // Ensure immediate update/ Đảm bảo cập nhật ngay lập tức
        }
    }

    interface IOnClickProductItemListener {
        fun onClickItemProduct(product: Product)
    }
}

