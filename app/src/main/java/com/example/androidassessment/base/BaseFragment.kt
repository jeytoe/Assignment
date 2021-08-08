package com.example.androidassessment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding: ViewBinding> : Fragment() {

  protected lateinit var binding: VBinding

  @StringRes
  protected abstract fun getToolbarTitle(): Int

  @DrawableRes
  protected open fun getToolbarLogo(): Int {
    return 0
  }

  protected open fun enableBackButton(): Boolean {
    return false
  }

  protected abstract fun getViewBinding(): VBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = getViewBinding()
  }

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    setupToolbarTitle(getToolbarTitle())
    setupBackButton(enableBackButton())
    return binding.root
  }

  protected val supportActionBar: ActionBar?
    get() = (activity as AppCompatActivity).supportActionBar

  protected fun setupToolbarTitle(@StringRes string: Int) {
    val actionBar = supportActionBar
    if (string != 0) {
      actionBar?.setTitle(string)
    }
  }

  private fun setupBackButton(setupBackButton: Boolean) {
    supportActionBar?.setDisplayHomeAsUpEnabled(setupBackButton)
  }
}
