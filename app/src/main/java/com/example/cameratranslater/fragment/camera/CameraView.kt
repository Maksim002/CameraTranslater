package com.example.cameratranslater.fragment.camera

import com.example.cameratranslater.base.MvpView
import com.example.deleteafter.db.base.MvpPresenter

interface CameraView {
    interface View : MvpView {
    }

    interface Presenter : MvpPresenter<View> {
    }
}