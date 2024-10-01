package com.bangnv.coffeeorder.ui_mvp.home

import com.bangnv.coffeeorder.data.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val view: HomeMvpView) : HomeMvpPresent {
    private val api = RetrofitClient.instance
    private val disposables = CompositeDisposable() // To manage RxJava subscriptions


    override fun fetchProducts() {
        val disposable = api.getProducts() // Giả định phương thức này trả về Observable<List<Product>>
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { products -> view.showProducts(products) },
                { error -> view.showError(error.message ?: "An error occurred") }
            )

        disposables.add(disposable)
    }

    override fun fetchCategories() {
        val disposable = api.getCategories() // Giả định phương thức này trả về Observable<List<Category>>
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { categories -> view.showCategories(categories) },
                { error -> view.showError(error.message ?: "An error occurred") }
            )

        disposables.add(disposable)
    }

    override fun clear() {
        disposables.clear() // Release resources / Clean up used resources
    }

}