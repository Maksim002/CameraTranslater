package com.example.cameratranslater.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.example.deleteafter.db.base.MvpPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : MvpView> : ViewModel(), MvpPresenter<V>, CoroutineScope{

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate

    private var view: V? = null

    @CallSuper
    override fun attach(view: V) {
        this.view = view
    }

    @CallSuper
    override fun detach() {
        coroutineContext.cancelChildren()
        view = null
    }
}