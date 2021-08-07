package com.example.androidassessment.login

import android.os.Bundle
import com.example.androidassessment.R
import com.example.androidassessment.base.BaseActivity
import com.example.androidassessment.common.AlertDialogFactory
import com.example.androidassessment.databinding.ActivityLoginBinding
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var dialogFactory: AlertDialogFactory

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun getToolbarTitle(): Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun onResume() {
        super.onResume()
        bindEvents()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun bindEvents() {
        compositeDisposable.add(viewModel.setupBtnLoginStateEvent(
            RxTextView.textChanges(binding.username),
            RxTextView.textChanges(binding.password)
        ).subscribe {
            binding.login.isEnabled = it
        })
        compositeDisposable.add(RxView.clicks(binding.login)
            .subscribe {
                viewModel.loginButtonClicked.onNext(Unit)
            })
        compositeDisposable.add(viewModel.setupLoginEvents()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is LoginResult.Succeeded -> dialogFactory
                        .getOkDialog(this, R.string.welcome)
                        .show()

                    is LoginResult.Failed -> dialogFactory
                        .getOkDialog(this, R.string.not_welcome)
                        .show()

                }
            })
    }
}
