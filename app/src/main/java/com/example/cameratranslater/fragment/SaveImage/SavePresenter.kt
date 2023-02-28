package com.example.cameratranslater.fragment.SaveImage

import com.example.cameratranslater.base.BasePresenter

class SavePresenter : BasePresenter<SaveView.View>(), SaveView.Presenter {

    override fun attach(view: SaveView.View) {
        super.attach(view)
        println()
    }
}