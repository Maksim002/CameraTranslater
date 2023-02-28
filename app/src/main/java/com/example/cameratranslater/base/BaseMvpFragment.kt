package com.example.cameratranslater.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.example.deleteafter.db.base.MvpPresenter
import kotlin.reflect.KClass

abstract class BaseMvpFragment<V : MvpView, P : MvpPresenter<V>> : Fragment(), MvpView {

    abstract val presenter: P

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        presenter.attach(this as V)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

}