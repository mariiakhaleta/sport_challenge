package com.example.sportchallenge.presentation.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.sportchallenge.R
import com.example.sportchallenge.data.entity.User
import com.example.sportchallenge.databinding.UserLeaderboardItemBinding


class LeaderboardAdapter : ListAdapter<User, ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_leaderboard_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}

class ViewHolder constructor(private val binding: UserLeaderboardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User) {
        binding.apply {
            this.user = item
        }
        binding.executePendingBindings()
    }
}
