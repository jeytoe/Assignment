package com.example.androidassessment.login.extension

import android.text.TextUtils
import android.util.Patterns


fun CharSequence.isValidEmailAddress(): Boolean {
    return !TextUtils.isEmpty(this) &&
            Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

