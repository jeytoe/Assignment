package com.example.androidassessment.common

import android.content.Context
import android.content.DialogInterface
import android.widget.ListAdapter
import androidx.annotation.CheckResult
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.example.androidassessment.R
import javax.inject.Inject

class AlertDialogFactory @Inject constructor() {

    @CheckResult
    fun getDialog(
        context: Context?,
        adapter: ListAdapter?,
        listener: DialogInterface.OnClickListener?
    ): AlertDialog {
        val builder = AlertDialog.Builder(
            context!!
        )
        builder.setAdapter(adapter, listener)
        return builder.create()
    }

    @CheckResult
    fun getOkDialog(context: Context?, @StringRes messageResId: Int): AlertDialog {
        return getOkDialog(context, 0, messageResId)
    }

    @CheckResult
    fun getOkDialog(
        context: Context?, @StringRes titleResId: Int,
        @StringRes messageResId: Int
    ): AlertDialog {
        val builder = AlertDialog.Builder(
            context!!
        )
        if (titleResId != 0) {
            builder.setTitle(titleResId)
        }
        return builder
            .setMessage(messageResId)
            .setPositiveButton(R.string.okay, null)
            .setCancelable(false)
            .create()
    }

    @CheckResult
    fun getOkDialog(
        context: Context?, @StringRes titleResId: Int,
        @StringRes messageResId: Int, cancelable: Boolean,
        onClickListener: DialogInterface.OnClickListener?
    ): AlertDialog {
        val builder = AlertDialog.Builder(
            context!!
        )
        if (titleResId != 0) {
            builder.setTitle(titleResId)
        }
        //    styleDialog(context, dialog);
        return builder
            .setMessage(messageResId)
            .setPositiveButton(R.string.okay) { d: DialogInterface, which: Int ->
                onClickListener?.onClick(d, which)
                d.dismiss()
            }
            .setCancelable(cancelable)
            .create()
    }
}
