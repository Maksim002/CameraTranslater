package com.example.cameratranslater.fragment.camera

import com.example.cameratranslater.base.BasePresenter

class CameraPresenter : BasePresenter<CameraView.View>(), CameraView.Presenter {

    override fun attach(view: CameraView.View) {
        super.attach(view)
        println()
    }
}