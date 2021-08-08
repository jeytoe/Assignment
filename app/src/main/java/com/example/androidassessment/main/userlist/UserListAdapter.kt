package com.example.androidassessment.main.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassessment.component.modules.network.userlist.NetworkUser
import com.example.androidassessment.databinding.ViewUserListItemBinding
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class UserListAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var models: List<NetworkUser> = emptyList()

    var onClickEvent: BehaviorSubject<NetworkUser> = BehaviorSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewUserListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding, onClickEvent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = models[position]
        (holder as UserViewHolder).bindView(model)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    fun setItems(models: List<NetworkUser>) {
        this.models = models
        notifyDataSetChanged()
    }
}
