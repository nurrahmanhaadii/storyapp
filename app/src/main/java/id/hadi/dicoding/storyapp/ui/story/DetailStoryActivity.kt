package id.hadi.dicoding.storyapp.ui.story

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import id.hadi.dicoding.storyapp.data.model.Resource
import id.hadi.dicoding.storyapp.data.network.response.Story
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import id.hadi.dicoding.storyapp.databinding.ActivityDetailStoryBinding
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.helper.parcelable
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
import id.hadi.dicoding.storyapp.ui.home.StoryViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val viewModel: StoryViewModel by viewModels()
    private var story: Story? = null
    private val loading: LoadingDialog by lazy { LoadingDialog(this) }

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    private fun getData() {
        story = intent.parcelable<Story>("story_key")
        story?.id?.let {
            viewModel.getDetailStory(it).observe(this) { resources ->
                when (resources) {
                    Resource.Loading -> loading.show()
                    is Resource.Success -> {
                        loading.dismiss()
                        val data = resources.data as StoryResponse
                        val story = data.story
                        binding.apply {
                            glide.load(story.photoUrl).into(ivPhoto)
                            tvDetailName.text = story.name
                            tvDescription.text = story.description
                        }
                    }
                    is Resource.Error -> {
                        loading.dismiss()
                        Utils.showSnackBar(binding.root, resources.data.toString())
                    }
                }
            }
        }
    }
}