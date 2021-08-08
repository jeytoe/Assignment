package com.example.androidassessment.main.userlist

import androidx.lifecycle.ViewModel
import com.example.androidassessment.component.modules.network.userlist.NetworkUser
import com.example.androidassessment.component.modules.network.userlist.UserListService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Inject

class UserListViewModel @Inject constructor(
    var compositeDisposable: CompositeDisposable,
    var userListService: UserListService
) : ViewModel() {

    fun getUserList(): Observable<List<NetworkUser>> {
        return userListService.getUserList()
//        return Observable.empty()
    }
}
