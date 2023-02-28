package com.example.cameratranslater.fragment.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.cameratranslater.R
import com.example.cameratranslater.base.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_camera.*
import java.text.SimpleDateFormat


class CameraFragment : BaseMvpFragment<CameraView.View, CameraView.Presenter>(), CameraView.View {

    override var presenter: CameraView.Presenter = CameraPresenter()
//    private lateinit var db: AppDataBase
    private val simpleDateTime = SimpleDateFormat("yyyy.MM.dd h:mm")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
        layoutCon.isVisible = true
    }

    private fun initClick() {
        btnPhoto.setOnClickListener {
            camera.captureImage {_, bytes ->
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                ivCrop.setImageToCrop(bitmap)
                layoutCon.isVisible = false
                ivCrop.isVisible = true
                btnOk.isVisible = true
            }
        }

        btnOk.setOnClickListener {
            val crop: Bitmap = ivCrop.crop()
            crop
        }
    }

    override fun onStart() {
        super.onStart()
        camera.onStart()
    }

    override fun onResume() {
        super.onResume()
        camera.onResume()
    }

    override fun onPause() {
        camera.onPause()
        super.onPause()
    }

    override fun onStop() {
        camera.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showErrorMessage(e: Throwable?, dismissCallback: (() -> Unit)?) {}

    override fun showErrorMessage(messageRes: Int) {}
}