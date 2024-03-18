package id.hadi.dicoding.storyapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import id.hadi.dicoding.storyapp.data.network.response.Story
import id.hadi.dicoding.storyapp.databinding.ItemStoryBinding

/**
 * Created by nurrahmanhaadii on 13,March,2024
 */
class StoryAdapter(val glide: RequestManager): RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            glide.load(item.photoUrl).into(binding.ivItemPhoto)
            binding.tvDetailName.text = item.name

            itemView.setOnClickListener {
                onItemClickListener.onStoryClicked(item)
            }
        }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }

    }

    private val listDiffer = AsyncListDiffer(this, diffUtil)

    var items: List<Story>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    private lateinit var onItemClickListener: ItemClickListener

    fun setItemClickListener(onItemClickListener: ItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

interface ItemClickListener {
    fun onStoryClicked(item: Story)
}