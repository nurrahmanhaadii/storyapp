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
import dagger.hilt.android.EntryPointAccessors
import id.haadii.dicoding.submission.favorite.databinding.ActivityFavoriteBinding
import id.haadii.dicoding.submission.favorite.di.DaggerFavoriteComponent
import id.hadi.dicoding.storyapp.di.FavoriteModuleDependencies
import id.hadi.dicoding.storyapp.ui.home.MainActivity.Companion.RESULT_ADD_STORY_SUCCESS
import id.hadi.dicoding.storyapp.ui.story.DetailStoryActivity
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity(), ItemClickListener {

    @Inject
    lateinit var viewModel: FavoriteViewModel

    private lateinit var binding: ActivityFavoriteBinding

    private var favoriteAdapter = FavoriteAdapter()

    private val addStoryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_ADD_STORY_SUCCESS) {
                getFavorite()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder().appDependencies(
            EntryPointAccessors.fromApplication(
                applicationContext,
                FavoriteModuleDependencies::class.java
            )
        ).context(this).build().inject(this)
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