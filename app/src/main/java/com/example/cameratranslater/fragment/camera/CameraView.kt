package com.example.cameratranslater.fragment.camera

import com.example.cameratranslater.base.MvpView
import com.example.deleteafter.db.base.MvpPresenter
import java.io.File

interface CameraView {
    interface View : MvpView {
        fun takePhoto(isFlashEnabled: Boolean = false)
        fun setResultAndFinish()
        fun setTempFile(file: File)
    }

    interface Presenter : MvpPresenter<View> {
        fun onTakePhotoButtonPressed()
    }
}