package com.mask.androidcompose_demo.utils

import android.widget.Toast
import com.mask.androidcompose_demo.App

/**
 * ToastUtils
 *
 * Create by lishilin on 2025-04-30
 */
object ToastUtils {

    fun show(msg: String) {
        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show()
    }

}