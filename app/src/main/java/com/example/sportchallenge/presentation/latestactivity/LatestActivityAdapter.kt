package com.example.sportchallenge.presentation.latestactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sportchallenge.data.dao.Workout
import com.example.sportchallenge.R
import com.example.sportchallenge.databinding.ActivityItemBinding

class LatestActivityAdapter : ListAdapter<Workout, ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.activity_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Workout>() {
            override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean =
                oldItem == newItem
        }
    }
}

class ViewHolder constructor(private val binding: ActivityItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Workout) {
        binding.apply {
            this.workout = item
        }
        if (item.earnedPoints.isNullOrEmpty()) {
            item.earnedPoints = "0"
        }
        binding.executePendingBindings()
    }
}
