package com.example.sportchallenge.presentation.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.sportchallenge.data.dao.ConnectedApp
import com.example.sportchallenge.R
import com.example.sportchallenge.databinding.AppsItemsBinding

class ConnectedAppsAdapter(private val onItemClicked: (ConnectedApp) -> Unit) :
    ListAdapter<ConnectedApp, ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.apps_items,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ConnectedApp>() {
            override fun areItemsTheSame(oldItem: ConnectedApp, newItem: ConnectedApp): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ConnectedApp, newItem: ConnectedApp): Boolean =
                oldItem == newItem
        }
    }
}

class ViewHolder constructor(private val binding: AppsItemsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ConnectedApp, onItemClicked: (ConnectedApp) -> Unit) {
        binding.apply {
            this.connectedApp = item
        }
        binding.checkboxApp.setOnClickListener {
            onItemClicked(item)
            item.synchronized = true
        }
        binding.executePendingBindings()
    }
}
