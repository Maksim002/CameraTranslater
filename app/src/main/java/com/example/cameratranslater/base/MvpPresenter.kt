package com.example.deleteafter.db.base

import com.example.cameratranslater.base.MvpView

interface MvpPresenter<V : MvpView> {

    fun attach(view: V)

    fun detach()
}