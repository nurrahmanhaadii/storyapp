package id.hadi.dicoding.storyapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import id.hadi.dicoding.storyapp.data.network.response.Story
import id.hadi.dicoding.storyapp.databinding.ItemStoryBinding

/**
 * Created by nurrahmanhaadii on 13,March,2024
 */
class StoryAdapter: PagingDataAdapter<Story, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    inner class StoryViewHolder(private val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            Glide.with(itemView.context).load(item.photoUrl)
                .into(binding.ivItemPhoto)
            binding.tvDetailName.text = item.name
            binding.tvDescription.text = item.description

            itemView.setOnClickListener {
                onItemClickListener.onStoryClicked(item)
            }
        }
    }

    private lateinit var onItemClickListener: ItemClickListener

    fun setItemClickListener(onItemClickListener: ItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

interface ItemClickListener {
    fun onStoryClicked(item: Story)
}