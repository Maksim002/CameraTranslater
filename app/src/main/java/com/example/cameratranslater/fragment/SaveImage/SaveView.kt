package com.example.cameratranslater.fragment.SaveImage

import com.example.cameratranslater.base.MvpView
import com.example.deleteafter.db.base.MvpPresenter

interface SaveView {
    interface View : MvpView {
    }

    interface Presenter : MvpPresenter<View> {
    }
}