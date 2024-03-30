package id.hadi.dicoding.storyapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.hadi.dicoding.storyapp.R
import id.hadi.dicoding.storyapp.data.model.Resource
import id.hadi.dicoding.storyapp.data.network.response.Story
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import id.hadi.dicoding.storyapp.databinding.ActivityMainBinding
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.ui.auth.AuthViewModel
import id.hadi.dicoding.storyapp.ui.auth.LoginActivity
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
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
            adapter = storyAdapter
        }
    }

    private fun getAllStories() {
        storyViewModel.getAllStories().observe(this) {
            when (it) {
                Resource.Loading -> loading.show()
                is Resource.Success -> {
                    loading.dismiss()
                    val data = it.data as StoryResponse
                    if (data.listStory.isEmpty()) {
                        binding.tvNoItem.isVisible = false
                        return@observe
                    }
                    storyAdapter.items = data.listStory
                }
                is Resource.Error -> {
                    loading.dismiss()
                    Utils.showSnackBar(binding.root, it.data.toString())
                }
            }
        }
    }

    override fun onStoryClicked(item: Story) {
        val intent = Intent(this, DetailStoryActivity::class.java)
        intent.putExtra("story_key", item)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> logout()
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

    companion object {
        const val RESULT_ADD_STORY_SUCCESS = 29
    }
}