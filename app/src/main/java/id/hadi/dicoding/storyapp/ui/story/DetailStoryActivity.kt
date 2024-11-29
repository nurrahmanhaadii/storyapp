package id.hadi.dicoding.storyapp.ui.story

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import id.haadii.dicoding.submission.domain.model.Resource
import id.hadi.dicoding.storyapp.R
import id.hadi.dicoding.storyapp.databinding.ActivityDetailStoryBinding
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.helper.parcelable
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
import id.hadi.dicoding.storyapp.ui.home.MainActivity
import id.hadi.dicoding.storyapp.ui.home.StoryViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val viewModel: StoryViewModel by viewModels()
    private var story: id.haadii.dicoding.submission.domain.model.Story? = null
    private val loading: LoadingDialog by lazy { LoadingDialog(this) }

    @Inject
    lateinit var glide: RequestManager

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        getIsFavorite()
        binding.fabFavorite.setOnClickListener {
            setResult(MainActivity.RESULT_ADD_STORY_SUCCESS)
            setFavorite()
        }
    }

    private fun getData() {
        story = intent.parcelable<id.haadii.dicoding.submission.domain.model.Story>("story_key")
        story?.id?.let {
            viewModel.getDetailStory(it).observe(this) { resources ->
                when (resources) {
                    Resource.Loading -> loading.show()
                    is Resource.Success -> {
                        loading.dismiss()
                        val data = resources.data as id.haadii.dicoding.submission.domain.model.StoryBase
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

    private fun getIsFavorite() {
        viewModel.getIsFavorite(story?.id ?: "").observe(this) {
            isFavorite = it
            updateFavoriteBtn()
        }
    }

    private fun updateFavoriteBtn() {
        binding.fabFavorite.setImageResource(
            if (isFavorite) {
                R.drawable.ic_favorite_filled
            } else {
                R.drawable.ic_favorite
            }
        )
    }

    private fun setFavorite() {
        isFavorite = !isFavorite
        viewModel.setFavorite(isFavorite, story?.id ?: "", story ?: return)
        updateFavoriteBtn()
    }
}