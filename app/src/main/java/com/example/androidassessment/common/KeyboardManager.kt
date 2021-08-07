package com.example.androidassessment.common

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import javax.inject.Inject

class KeyboardManager @Inject constructor() {

  fun closeKeyboard(context: Context, windowToken: IBinder?) {
    val inputMethodManager = context.getSystemService(
        Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
  }
}
