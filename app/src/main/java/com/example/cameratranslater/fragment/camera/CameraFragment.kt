package com.example.cameratranslater.fragment.camera

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cameratranslater.R
import com.example.cameratranslater.base.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.lang.Exception


class CameraFragment : BaseMvpFragment<CameraView.View, CameraView.Presenter>(), CameraView.View{

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 333
    }

    override var presenter: CameraView.Presenter = CameraPresenter()

    private var permissionsForRequest: Pair<String, Pair<() -> Unit, () -> Unit>>? = null

    private var imageCapture: ImageCapture? = null
    private lateinit var tempFile: File
    private lateinit var photoPath: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         withPermission(Manifest.permission.CAMERA, {
            try {
                startCamera()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }, { })

        wdwd.setOnClickListener { presenter.onTakePhotoButtonPressed() }
    }


    private fun withPermission(permission: String, granted: () -> Unit, denied: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            granted()
        } else {
            permissionsForRequest = Pair(permission, Pair(granted, denied))
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permission),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFeature.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()

            val size =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Size(1080, 1920)
                } else {
                    Size(1920, 1080)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val preview = Preview.Builder()
                .setTargetResolution(size)
                .build()
                .also {
                    it.setSurfaceProvider(pvCameraView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setTargetResolution(size)
                .build()

            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
            )

            val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    // Get the camera's current zoom ratio
                    val currentZoomRatio = camera.cameraInfo.zoomState.value?.zoomRatio ?: 0F

                    // Get the pinch gesture's scaling factor
                    val delta = detector.scaleFactor

                    // Update the camera's zoom ratio. This is an asynchronous operation that returns
                    // a ListenableFuture, allowing you to listen to when the operation completes.
                    camera.cameraControl.setZoomRatio(currentZoomRatio * delta)

                    // Return true, as the event was handled
                    return true
                }
            }
            val scaleGestureDetector = ScaleGestureDetector(requireContext(), listener)

            // Attach the pinch gesture listener to the viewfinder
            pvCameraView.setOnTouchListener { _, event ->
                scaleGestureDetector.onTouchEvent(event)
                return@setOnTouchListener true
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun takePhoto(isFlashEnabled: Boolean) {
        val imageCapture = imageCapture ?: return
        val photoFile = tempFile
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.flashMode = if (isFlashEnabled) {
            ImageCapture.FLASH_MODE_ON
        } else {
            ImageCapture.FLASH_MODE_OFF
        }
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(requireContext()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        dwdwdw.setImageDrawable(Drawable.createFromPath(tempFile.path))
//                        setResultAndFinish()
                    }

                    override fun onError(exception: ImageCaptureException) {
                        exception
                    }
                })
    }

    override fun setResultAndFinish() {
        try {
            val photoFile = File(photoPath)
            if (!tempFile.exists()) {
                tempFile.mkdir()
            }
            if (!photoFile.exists()) {
                photoFile.mkdir()
            }
            tempFile.copyTo(photoFile, true)
        }catch (e: Exception){
            e
        }
    }

    override fun setTempFile(file: File) {
        tempFile = file
    }

    override fun showErrorMessage(e: Throwable?, dismissCallback: (() -> Unit)?) {}

    override fun showErrorMessage(messageRes: Int) {}
}