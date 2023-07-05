package com.example.cameratranslater.fragment.camera

import com.example.cameratranslater.base.BasePresenter
import java.io.File

class CameraPresenter : BasePresenter<CameraView.View>(), CameraView.Presenter {

    override fun attach(view: CameraView.View) {
        super.attach(view)
        view.setTempFile(File.createTempFile("temp", ".jpg"))
    }

    override fun onTakePhotoButtonPressed() {
        view?.takePhoto(false)
    }
}