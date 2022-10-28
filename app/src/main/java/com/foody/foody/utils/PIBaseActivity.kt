package com.foody.foody.utils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.foody.foody.ui.progress.ProgressFragment

open class PIBaseActivity : AppCompatActivity() {

    val TAG = PIBaseActivity::class.java.name
    private var mLoaderFragment: ProgressFragment? = null

    fun showProgressDialog(tag: String) {
        Log.d(TAG, "Show progress bar")
        val curr = supportFragmentManager.findFragmentByTag(tag)
        if (curr != null) {
            val dialogFragment = curr as? DialogFragment
            dialogFragment?.dismiss()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(curr)
            transaction.commit()
        }
        mLoaderFragment = ProgressFragment()
        mLoaderFragment?.isCancelable = false

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(mLoaderFragment!!, tag)
        transaction.commitAllowingStateLoss()
    }

    fun dismissProgressDialog(tag: String) {
        mLoaderFragment?.let {
            Log.d(TAG, "Dismiss progress bar")
            it.dismissAllowingStateLoss()
        }
    }
}