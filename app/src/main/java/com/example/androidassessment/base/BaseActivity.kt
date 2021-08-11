package com.example.androidassessment.base


import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import com.example.androidassessment.R
import com.example.androidassessment.databinding.ActivityBaseBinding

abstract class BaseActivity<VBinding : ViewBinding> : AppCompatActivity() {

    private lateinit var baseBinding: ActivityBaseBinding

    lateinit var binding: VBinding

    protected abstract fun getViewBinding(): VBinding

    protected abstract fun getToolbarTitle(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        binding = getViewBinding()
        setContentView(baseBinding.root)
        setupContentView()
        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }

    protected open fun enableBackButton(): Boolean {
        return false
    }

    protected open fun enableCloseButton(): Boolean {
        return false
    }

    protected open fun navigateUp() {
        NavUtils.navigateUpFromSameTask(this)
    }

    protected fun setupToolbarTitle(@StringRes string: Int) {
        val actionBar = supportActionBar
        if (actionBar != null && string != 0) {
            actionBar.setTitle(string)
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(enableBackButton() || enableCloseButton())
            if (getToolbarTitle() != 0) {
                supportActionBar.title = getString(getToolbarTitle())
            } else {
                supportActionBar.title = null
            }
            if (enableCloseButton()) {
                supportActionBar.setHomeAsUpIndicator(R.drawable.ic_close)
            }
        }
    }

    private fun setupContentView() {
        val viewGroup = baseBinding.mainContent
        viewGroup.addView(binding.root, viewGroup.layoutParams)
    }
}
