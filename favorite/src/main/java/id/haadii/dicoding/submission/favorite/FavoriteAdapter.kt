package id.haadii.dicoding.submission.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hadi.dicoding.storyapp.databinding.ItemStoryBinding
import id.hadi.dicoding.storyapp.domain.model.Story

/**
 * Created by nurrahmanhaadii on 13,March,2024
 */
class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(private val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            Glide.with(itemView.context).load(item.photoUrl)
                .into(binding.ivItemPhoto)
            binding.tvDetailName.text = item.name
            binding.tvDescription.text = item.description

            binding.imgFav.isVisible = item.isFavorite

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

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private val listDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    var items: List<Story>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

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