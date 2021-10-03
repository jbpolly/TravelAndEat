package com.mysticraccoon.travelandeat.ui.selectLocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mysticraccoon.travelandeat.R
import com.mysticraccoon.travelandeat.databinding.FragmentSelectLocationBinding
import com.mysticraccoon.travelandeat.ui.addEditMeal.AddEditMealViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class SelectLocationFragment: Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: AddEditMealViewModel by sharedViewModel()
    private lateinit var binding: FragmentSelectLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_location, container, false)
        binding.lifecycleOwner = this
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }


    override fun onMapReady(readyMap: GoogleMap) {
        map = readyMap
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setMyLocation()
        }else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                displayFineLocationPermissionRationale()
            } else {
                requestFineLocationPermissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
        setMapLongClick(map)
        setPoiClick(map)

    }

    private val requestFineLocationPermissionLaunch =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setMyLocation()
            }else{
                Toast.makeText(requireContext(), getString(R.string.permission_my_location_denied), Toast.LENGTH_LONG).show()
            }
        }

    private fun displayFineLocationPermissionRationale() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.fine_location_title))
            .setMessage(getString(R.string.my_location_permission_description))
            .setPositiveButton(getString(R.string.text_continue)) { _, _ ->
                requestFineLocationPermissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(requireContext(), getString(R.string.permission_feature_denied), Toast.LENGTH_SHORT).show()
            }
            .show()
    }



    @SuppressLint("MissingPermission")
    private fun setMyLocation() {
        val zoomLevel = 13f
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                location?.let {
                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                it.latitude,
                                it.longitude
                            ), zoomLevel
                        )
                    )
                }
            }
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )

            val marker = map.addMarker(
                MarkerOptions().position(latLng).title(getString(R.string.location))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
            marker?.title = snippet
            requestPositionConfirmation(latLng, snippet)
        }

    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker?.showInfoWindow()
            requestPositionConfirmation(poi.latLng, poi.name)
        }
    }

    private fun requestPositionConfirmation(latLong: LatLng, title: String){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.location))
            .setMessage(getString(R.string.are_you_sure_location_meal, title))
            .setPositiveButton(getString(R.string.text_continue)) { _, _ ->
                onLocationSelected(latLong, title)
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun onLocationSelected(latLong: LatLng, title: String) {
        viewModel.setMealLocation(latLong, title)
        findNavController().popBackStack()
    }






}