package com.example.androidassessment.main.userlist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassessment.R
import com.example.androidassessment.base.BaseFragment
import com.example.androidassessment.common.AlertDialogFactory
import com.example.androidassessment.databinding.FragmentUserListBinding
import com.example.androidassessment.main.LogoutAction
import com.example.androidassessment.main.MainActivity
import com.google.gson.Gson
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

    @Inject
    lateinit var gson: Gson

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.log_out -> {
                userListViewModel.logout()
                (activity as LogoutAction).logout()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(
            userListViewModel.getUserList()
                    //here we can show loading dialog by using doOnSubscribe and doOnTerminate
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
                val action =
                    UserListFragmentDirections
                        .listToDetails(gson.toJson(it))
                findNavController().navigate(action)
            })
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
