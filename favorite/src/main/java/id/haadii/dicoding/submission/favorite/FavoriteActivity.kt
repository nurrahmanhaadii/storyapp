package id.haadii.dicoding.submission.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.haadii.dicoding.submission.core.StoryPreferenceManager
import id.haadii.dicoding.submission.core.local.database.StoryDatabase
import id.haadii.dicoding.submission.core.network.api.RetrofitBuilder
import id.haadii.dicoding.submission.core.repositories.MainRepositoryImpl
import id.haadii.dicoding.submission.domain.usecase.StoryInteractor
import id.haadii.dicoding.submission.favorite.databinding.ActivityFavoriteBinding
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
import id.hadi.dicoding.storyapp.ui.home.MainActivity.Companion.RESULT_ADD_STORY_SUCCESS
import id.hadi.dicoding.storyapp.ui.story.DetailStoryActivity

class FavoriteActivity : AppCompatActivity(), ItemClickListener {
    private val apiService by lazy { RetrofitBuilder.apiService(this) }
    private val dataStoryPreferenceManager by lazy { StoryPreferenceManager(this) }
    private val database by lazy { StoryDatabase.getDatabase(this) }
    private val mainRepository by lazy { MainRepositoryImpl(apiService, dataStoryPreferenceManager, database) }
    private val useCase by lazy { StoryInteractor(mainRepository) }
    private val viewModel: FavoriteViewModel by viewModelFactory { FavoriteViewModel(useCase) }
    private val loading: LoadingDialog by lazy { LoadingDialog(this) }
    private lateinit var binding: ActivityFavoriteBinding

    private var favoriteAdapter = FavoriteAdapter()

    private val addStoryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_ADD_STORY_SUCCESS) {
            getFavorite()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerview()
        getFavorite()

        binding.edPlace.doAfterTextChanged {
            viewModel.searchFavorite(it.toString()).observe(this) { data ->
                favoriteAdapter.items = data
            }
        }
    }

    private fun initRecyclerview() {
        binding.rvStory.apply {
            favoriteAdapter.setItemClickListener(this@FavoriteActivity)

            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = favoriteAdapter
        }
    }

    private fun getFavorite() {
        viewModel.getFavorite().observe(this) {
            favoriteAdapter.items = it
        }
    }

    override fun onStoryClicked(item: id.haadii.dicoding.submission.domain.model.Story) {
        val intent = Intent(this, DetailStoryActivity::class.java)
        intent.putExtra("story_key", item)
        addStoryLauncher.launch(intent)
    }


}