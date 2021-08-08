package com.example.androidassessment.main.userlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassessment.R
import com.example.androidassessment.base.BaseFragment
import com.example.androidassessment.common.AlertDialogFactory
import com.example.androidassessment.databinding.FragmentUserListBinding
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserListFragment : BaseFragment<FragmentUserListBinding>() {

    @Inject
    lateinit var userListViewModel: UserListViewModel

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var alertDialogFactory: AlertDialogFactory

    @Inject
    lateinit var adapter: UserListAdapter

    override fun getToolbarTitle(): Int = R.string.user_list_title

    override fun getViewBinding(): FragmentUserListBinding {
        return FragmentUserListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, parent, savedInstanceState)
        binding.userList.layoutManager = LinearLayoutManager(context)
        binding.userList.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(
            userListViewModel.getUserList()
                .subscribe({
                    adapter.setItems(it)
                }, {
                    alertDialogFactory.getOkDialog(
                        this.context,
                        R.string.get_user_list_failed
                    ).show()
                })
        )
        compositeDisposable.add(adapter.onClickEvent
            .subscribe {
                Log.e("hehehe", "clicked ${it.email}")
            })
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
