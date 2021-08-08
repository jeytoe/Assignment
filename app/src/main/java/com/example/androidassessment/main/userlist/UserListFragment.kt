package com.example.androidassessment.main.userlist

import android.os.Bundle
import com.example.androidassessment.R
import com.example.androidassessment.base.BaseFragment
import com.example.androidassessment.databinding.FragmentUserListBinding
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserListFragment : BaseFragment<FragmentUserListBinding>() {

    @Inject
    lateinit var userListViewModel: UserListViewModel

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    override fun getToolbarTitle(): Int = R.string.user_list_title

    override fun getViewBinding(): FragmentUserListBinding {
        return FragmentUserListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(
            userListViewModel.getUserList()
                .subscribe({
                    print("hello")
                }, {
                    print("helloooo")
                })
        )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
