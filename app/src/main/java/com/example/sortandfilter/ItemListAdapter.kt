package com.example.sortandfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sortandfilter.database.Item
import com.example.sortandfilter.databinding.LayoutItemBinding

class ItemViewHolder(
    private val binding: LayoutItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item) {
        binding.tvId.text = "${item.id}"
        binding.tvTitle.text = item.title
    }
}

private val DIFF_UTIL_ITEM_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }
}

class ItemListAdapter : ListAdapter<Item, ItemViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
