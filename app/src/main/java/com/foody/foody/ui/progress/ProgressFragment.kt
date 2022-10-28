package com.foody.foody.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.foody.foody.R

class ProgressFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val loaderView = inflater.inflate(R.layout.fragment_progress, container, false)
        isCancelable = false
        loaderView.findViewById<ProgressBar>(R.id.commonProgressBar).visibility = View.VISIBLE
        return loaderView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val window = it.window
            window?.setBackgroundDrawableResource(android.R.color.transparent)

            val windowParams = window?.attributes
            windowParams?.dimAmount = 0.1f
            windowParams?.flags != WindowManager.LayoutParams.FLAG_DIM_BEHIND
            window?.attributes = windowParams
        }
    }
}