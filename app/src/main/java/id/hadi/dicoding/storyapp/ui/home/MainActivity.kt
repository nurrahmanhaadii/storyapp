package id.hadi.dicoding.storyapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.map
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint
import id.haadii.dicoding.submission.core.model.Resource
import id.hadi.dicoding.storyapp.R
import id.hadi.dicoding.storyapp.databinding.ActivityMainBinding
import id.hadi.dicoding.storyapp.domain.model.Story
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.helper.mapToDomain
import id.hadi.dicoding.storyapp.ui.auth.AuthViewModel
import id.hadi.dicoding.storyapp.ui.auth.LoginActivity
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
import id.hadi.dicoding.storyapp.ui.base.LoadingStateAdapter
import id.hadi.dicoding.storyapp.ui.map.MapsActivity
import id.hadi.dicoding.storyapp.ui.story.AddStoryActivity
import id.hadi.dicoding.storyapp.ui.story.DetailStoryActivity
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()
    private val storyViewModel: StoryViewModel by viewModels()
    private val loading: LoadingDialog by lazy { LoadingDialog(this) }

    @Inject
    lateinit var storyAdapter: StoryAdapter

    private val addStoryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_ADD_STORY_SUCCESS) {
            getAllStories()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initRecyclerview()
        getAllStories()
        binding.fab.setOnClickListener {
            goToAddStoryPage()
        }
    }

    private fun initRecyclerview() {
        binding.rvStory.apply {
            storyAdapter.setItemClickListener(this@MainActivity)

            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
        }
    }

    private fun getAllStories() {
        storyViewModel.getAllStories().observe(this) {
            storyAdapter.submitData(lifecycle, it.map { data -> data.mapToDomain() })
        }
    }

    override fun onStoryClicked(item: Story) {
        val intent = Intent(this, DetailStoryActivity::class.java)
        intent.putExtra("story_key", item)
        addStoryLauncher.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> installFavoriteModule()
            R.id.action_logout -> logout()
            R.id.action_map -> goToMapPage()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun logout() {
        viewModel.logout().observe(this) {
            when (it) {
                Resource.Loading -> loading.show()
                is Resource.Success -> {
                    loading.dismiss()
                    goToLoginPage()
                }
                is Resource.Error -> {
                    loading.dismiss()
                    Utils.showSnackBar(binding.root, it.data.toString())
                }
            }
        }
    }

    private fun goToLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToAddStoryPage() {
        val intent = Intent(this, AddStoryActivity::class.java)
        addStoryLauncher.launch(intent)
    }

    private fun goToMapPage() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleChat = "favorite"
        if (splitInstallManager.installedModules.contains(moduleChat)) {
            goToFavoritePage()
            Toast.makeText(this, "Open module", Toast.LENGTH_SHORT).show()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleChat)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    goToFavoritePage()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module: $it", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun goToFavoritePage() {
        val intent = Intent(this, Class.forName("id.haadii.dicoding.submission.favorite.FavoriteActivity"))
        startActivity(intent)
    }

    companion object {
        const val RESULT_ADD_STORY_SUCCESS = 29
    }
}