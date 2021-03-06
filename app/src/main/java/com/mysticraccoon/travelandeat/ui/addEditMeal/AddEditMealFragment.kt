package com.mysticraccoon.travelandeat.ui.addEditMeal

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.mysticraccoon.travelandeat.BR
import com.mysticraccoon.travelandeat.R
import com.mysticraccoon.travelandeat.core.utils.safeNavigate
import com.mysticraccoon.travelandeat.databinding.FragmentAddEditMealBinding
import com.mysticraccoon.travelandeat.ui.components.EditTextDecimalTextWatcher
import com.mysticraccoon.travelandeat.core.geofence.GEOFENCE_TAG
import com.mysticraccoon.travelandeat.core.geofence.GeofenceBroadcastReceiver
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AddEditMealFragment : Fragment() {

    private lateinit var binding: FragmentAddEditMealBinding
    private val viewModel: AddEditMealViewModel by sharedViewModel()
    private lateinit var geofencingClient: GeofencingClient
    private val args: AddEditMealFragmentArgs by navArgs()

    private var mealPriceTextWatcher: TextWatcher? = null

    // A PendingIntent for the Broadcast Receiver that handles geofence transitions.
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditMealBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner

        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        setupMoneyEditText()
        setupScreen(args.isEdit)

        return binding.root
    }

    private fun setupMoneyEditText() {
        binding.mealPriceField.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        binding.mealPriceField.keyListener =
            DigitsKeyListener.getInstance("0123456789")
        mealPriceTextWatcher = EditTextDecimalTextWatcher(binding.mealPriceField)
        binding.mealPriceField.addTextChangedListener(mealPriceTextWatcher)
    }

    private fun setupScreen(isEdit: Boolean) {
        //todo setup screen to edit
        if (isEdit) {
            binding.deleteButton.visibility = View.VISIBLE
            viewModel.loadSavedPlace(args.savedPlace)
        } else {
            binding.deleteButton.visibility = View.GONE
            if (viewModel.savedPlace == null) {
                viewModel.createSavedPlace()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {

        binding.mealNameField.setOnClickListener {
            findNavController().safeNavigate(AddEditMealFragmentDirections.actionAddEditMealFragmentToSelectMealFragment())
        }

        binding.mealLocationField.setOnClickListener {
            findNavController().safeNavigate(AddEditMealFragmentDirections.actionAddEditMealFragmentToSelectLocationFragment())
        }

        viewModel.showSnackBar.observe(viewLifecycleOwner) { text ->
            Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT).show()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteSavedPlace()
        }

        viewModel.deletedComplete.observe(viewLifecycleOwner) { isComplete ->
            if (isComplete) {
                viewModel.clearSavedPlace()
                findNavController().safeNavigate(AddEditMealFragmentDirections.actionAddEditMealFragmentToDashboardFragment())
            }
        }

        binding.saveButton.setOnClickListener {
            saveMealPlace()
        }

        viewModel.saveComplete.observe(viewLifecycleOwner) { isComplete ->
            if (isComplete) {
                viewModel.clearSavedPlace()
                findNavController().safeNavigate(AddEditMealFragmentDirections.actionAddEditMealFragmentToDashboardFragment())
            }
        }

    }

    private fun saveMealPlace() {
        if (viewModel.validateEnteredData()) {
            checkDeviceLocationSettings()
        }
    }

    private val locationSettingsResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            Toast.makeText(requireContext(), "Resolved location settings", Toast.LENGTH_SHORT)
                .show()
            saveMealPlace()
        }


    private fun checkDeviceLocationSettings(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    locationSettingsResult.launch(intentSenderRequest)

                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(
                        GEOFENCE_TAG,
                        "Error getting location settings resolution: " + sendEx.message
                    )
                }
            } else {
                Snackbar.make(
                    binding.root,
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettings()
                }.show()
            }
        }

        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
                checkGeoFencePermissions()
                //Toast.makeText(requireContext(), "Checked location success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkGeoFencePermissions() {
        val backgroundLocation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else true

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && backgroundLocation
        ) {
            // Have all the necessary permission
            addGeofenceForReminder()
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    displayFineLocationPermissionRationale()
                } else {
                    requestFineLocationPermissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            } else {
                if (!backgroundLocation) {
                    //always display rationale to explain the need for background location
                    displayBackgroundLocationPermissionRationale()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun addGeofenceForReminder() {
        viewModel.savedPlace?.let { savedPlace ->
            // Build the geofence using the geofence builder
            val geofence = Geofence.Builder()
                .setRequestId(savedPlace.id)
                .setCircularRegion(
                    savedPlace.latitude ?: 0.0,
                    savedPlace.longitude ?: 0.0,
                    GEOFENCE_RADIUS_IN_METERS
                )
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build()

            //Build the geofence request.
            val geofencingRequest = GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build()

            geofencingClient.removeGeofences(listOf(savedPlace.id)).run {
                addOnCompleteListener {
                    geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
                        addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                R.string.geofences_added,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Log.e(GEOFENCE_TAG, geofence.requestId)
                            viewModel.saveSavedPlace()
                        }
                        addOnFailureListener {
                            // Failed to add geofences.
                            Toast.makeText(
                                requireContext(),
                                R.string.geofences_not_added,
                                Toast.LENGTH_SHORT
                            ).show()
                            if ((it.message != null)) {
                                Log.w(GEOFENCE_TAG, it.message ?: "")
                            }
                            viewModel.saveSavedPlace()
                        }
                    }
                }
            }
        }

    }


    private val requestFineLocationPermissionLaunch =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                saveMealPlace()
            }
        }

    private val requestBackgroundLocationPermissionLaunch =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                saveMealPlace()
            }
        }

    private fun displayFineLocationPermissionRationale() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.fine_location_title))
            .setMessage(getString(R.string.widget_background_location_description))
            .setPositiveButton(getString(R.string.text_continue)) { _, _ ->
                requestFineLocationPermissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    @SuppressLint("InlinedApi")
    private fun displayBackgroundLocationPermissionRationale() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.background_location_title))
            .setMessage(getString(R.string.widget_background_location_description))
            .setPositiveButton(getString(R.string.text_continue)) { _, _ ->
                requestBackgroundLocationPermissionLaunch.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        mealPriceTextWatcher?.let {
            binding.mealPriceField.removeTextChangedListener(it)
        }
        super.onDestroyView()
    }

    companion object {
        const val GEOFENCE_RADIUS_IN_METERS = 100f
        const val ACTION_GEOFENCE_EVENT = "reminder_geofence_event"
    }


}