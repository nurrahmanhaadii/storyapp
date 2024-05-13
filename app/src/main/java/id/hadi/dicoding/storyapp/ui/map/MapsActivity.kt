package id.hadi.dicoding.storyapp.ui.map

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.hadi.dicoding.storyapp.R
import id.hadi.dicoding.storyapp.data.model.Resource
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import id.hadi.dicoding.storyapp.databinding.ActivityMapsBinding
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
import id.hadi.dicoding.storyapp.ui.home.StoryViewModel

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val storyViewModel: StoryViewModel by viewModels()
    private val loading: LoadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getAllStoriesWithLocation()
        setMapStyle()
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(MapsActivity::class.simpleName, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(MapsActivity::class.simpleName, "Can't find style. Error: ", exception)
        }
    }

    private fun getAllStoriesWithLocation() {
        storyViewModel.getAllStoriesWithLocation().observe(this) {
            when (it) {
                Resource.Loading -> loading.show()
                is Resource.Success -> {
                    loading.dismiss()
                    val data = it.data as StoryResponse

                    addMarkers(data)
                }
                is Resource.Error -> {
                    loading.dismiss()
                    Utils.showSnackBar(binding.root, it.data.toString())
                }
            }
        }
    }

    private fun addMarkers(storyResponse: StoryResponse) {
        storyResponse.listStory.forEach { data ->
            val latLng = LatLng(data.lat, data.lon)
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(data.name)
                    .snippet(data.description)
            )
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 3f))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_maps, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}