package id.hadi.dicoding.storyapp.ui.story

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import id.hadi.dicoding.storyapp.data.model.Resource
import id.hadi.dicoding.storyapp.databinding.ActivityAddStoryBinding
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.helper.reduceFileImage
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
import id.hadi.dicoding.storyapp.ui.camera.CameraActivity
import id.hadi.dicoding.storyapp.ui.camera.CameraActivity.Companion.CAMERAX_RESULT
import id.hadi.dicoding.storyapp.ui.home.MainActivity
import id.hadi.dicoding.storyapp.ui.home.StoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding

    private var currentImageUri: Uri? = null
    private var lat: Double? = null
    private var long: Double? = null

    private val viewModel: StoryViewModel by viewModels()
    private val loading: LoadingDialog by lazy { LoadingDialog(this) }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(this, "No media selected", Toast.LENGTH_LONG).show()
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLastLocation()
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }

                else -> {
                    // No location access granted.
                    lat = null
                    long = null
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener { submitStory() }
        binding.switchLocation.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (!isChecked) {
                lat = null
                long = null
                return@setOnCheckedChangeListener
            }
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun submitStory() {
        val desc = binding.edAddDescription.text.toString()
        val file = Utils.uriToFile(this, currentImageUri)
        val targetSize = 1024 * 1024
        val imageFile = Utils.compressAndWriteImage(this, file?.path, targetSize.toLong())
        if (imageFile == null) {
            Toast.makeText(
                this,
                "Error submit Image, please try again add Image",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        viewModel.submitStory(desc, imageFile, lat, long).observe(this) { resources ->
            when (resources) {
                Resource.Loading -> loading.show()
                is Resource.Success -> {
                    loading.dismiss()
                    Toast.makeText(this, "Submit Story Succeeded", Toast.LENGTH_LONG).show()
                    setResult(MainActivity.RESULT_ADD_STORY_SUCCESS)
                    finish()
                }

                is Resource.Error -> {
                    loading.dismiss()
                    Utils.showSnackBar(binding.root, resources.data.toString())
                }
            }
        }
    }

    private fun getMyLastLocation() {
        if (Utils.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
            Utils.checkPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            locateMe()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun locateMe(
    ) {
        loading.show()
        CoroutineScope(Dispatchers.IO).launch {
            val priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val result = fusedLocationClient.getCurrentLocation(
                priority,
                CancellationTokenSource().token,
            )
            result.addOnCompleteListener { fetchedLocation ->
                loading.dismiss()
                if (fetchedLocation.result == null) {
                    Toast.makeText(
                        this@AddStoryActivity,
                        "Tidak berhasil untuk mendapatkan lokasi.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@addOnCompleteListener
                }
                lat = fetchedLocation.result.latitude
                long = fetchedLocation.result.longitude
                Toast.makeText(
                    this@AddStoryActivity,
                    "berhasil untuk mendapatkan lokasi: $lat, $long",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
                .addOnFailureListener {
                    loading.dismiss()
                    Toast.makeText(
                        this@AddStoryActivity,
                        "Tidak berhasil untuk mendapatkan lokasi. ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}