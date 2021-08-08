package com.example.androidassessment.main.userlist

import androidx.recyclerview.widget.RecyclerView
import com.example.androidassessment.component.modules.network.userlist.NetworkUser
import com.example.androidassessment.databinding.ViewUserListItemBinding
import io.reactivex.subjects.BehaviorSubject

class UserViewHolder(
    var binding: ViewUserListItemBinding,
    var onClickEvent: BehaviorSubject<NetworkUser>
) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bindView(user: NetworkUser) {
        binding.tvName.text = user.name
        binding.tvUsername.text = user.username
        itemView.setOnClickListener {
            onClickEvent.onNext(user)
        }
    }
}
