package com.bangnv.coffeeorder.ui_mvp.home

import com.bangnv.coffeeorder.model.Category
import com.bangnv.coffeeorder.model.Product

interface HomeMvpView {
    fun showProducts(products: List<Product>)
    fun showCategories(categories: List<Category>)
    fun showError(message: String)
}