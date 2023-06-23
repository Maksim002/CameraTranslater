package com.example.cameratranslater.fragment.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cameratranslater.R
import com.example.cameratranslater.base.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_camera.*


class CameraFragment : BaseMvpFragment<CameraView.View, CameraView.Presenter>(), CameraView.View{

    override var presenter: CameraView.Presenter = CameraPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val metrics = requireContext().resources.displayMetrics
        metrics.

        ratio
    }

    fun gcd(p: Int, q: Int): Int {
        return if (q == 0) p else gcd(q, p % q)
    }

    fun ratio(a: Int, b: Int) {
        val gcd: Int = gcd(a, b)
        if (a > b) {
            showAnswer(a / gcd, b / gcd)
        } else {
            showAnswer(b / gcd, a / gcd)
        }
    }

    fun showAnswer(a: Int, b: Int) {
        println("$a $b")
    }

    override fun onStart() {
        super.onStart()
        camera.open()
    }

    override fun showErrorMessage(e: Throwable?, dismissCallback: (() -> Unit)?) {}

    override fun showErrorMessage(messageRes: Int) {}
}