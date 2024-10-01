package com.bangnv.coffeeorder.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangnv.coffeeorder.databinding.ItemCategoryHomeBinding
import com.bangnv.coffeeorder.model.Category
import com.bangnv.coffeeorder.utils.GlideUtils

class CategoryAdapter(
    private var categories: List<Category>,
    private val iOnClickCategoryItemListener: IOnClickCategoryItemListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryHomeBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Category>) {
        Log.d("CategoryAdapter", "Categories updated: $newList")
        categories = newList
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {

            GlideUtils.loadUrl300(category.image, binding.imgCategory)
            binding.tvCategoryName.text = category.name

            binding.layoutItem.setOnClickListener {
                iOnClickCategoryItemListener.onClickItemCategory(category)
            }

            binding.executePendingBindings()  // Ensure immediate update: Đảm bảo cập nhật ngay lập tức
        }
    }

    interface IOnClickCategoryItemListener {
        fun onClickItemCategory(category: Category)
    }
}



