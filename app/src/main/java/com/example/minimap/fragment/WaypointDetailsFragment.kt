package com.example.minimap.fragment

import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.minimap.R
import com.example.minimap.databinding.WaypointDetailsFragmentBinding
import com.example.minimap.viewModel.ViewModel
import com.google.android.gms.maps.model.LatLng


class WaypointDetailsFragment : Fragment()
{
    private lateinit var binding : WaypointDetailsFragmentBinding
    private val viewModel : ViewModel by activityViewModels()
    /**
     * Gets the types from a string array and sets the adapter on the layout.
     */
    private fun restartTypes()
    {
        val types = resources.getStringArray( R.array.waypointTypes )

        val adapter = ArrayAdapter( requireContext(), R.layout.waypoint_type, types )

        binding.waypointTypeTextView.setAdapter( adapter )
    }
    /**
     * Sets the exposed drop down menu of waypoint types.
     */
    private fun initializeTypes()
    {
        restartTypes()
        binding.waypointTypeTextView.setText( binding.waypointTypeTextView.adapter.getItem( 0 ).toString(), false )
    }
    /**
     * From a string, sets the type of a waypoint on the exposed drop down menu.
     */
    private fun setType( type : String )
    {
        for ( i in 0 until binding.waypointTypeTextView.adapter.count )
        {
            val currentType = binding.waypointTypeTextView.adapter.getItem( i ).toString()
            if ( currentType == type ) binding.waypointTypeTextView.setText( currentType )
        }
        restartTypes()
    }
    /**
     * Sets the values of the selected waypoint on each view.
     */
    private fun initializeWaypointData()
    {
        initializeTypes()

        binding.img.setImageResource( R.drawable.image_unavailable )

        val creatingNewWaypoint = arguments?.getBoolean( "creatingNewWaypoint" )

        val id = arguments?.getInt( "waypointId" )
        val waypoint = viewModel.getWaypoints().find { it.id == id }

        if ( waypoint != null )
        {
            val waypointId = "Nº $id"

            val latitude = viewModel.roundToTwoDecimals( waypoint.position.latitude )
            val longitude = viewModel.roundToTwoDecimals( waypoint.position.longitude )

            binding.waypointId.text = waypointId

            binding.latitudeValueEditText.setText( latitude.toString() )
            binding.longitudeValueEditText.setText( longitude.toString() )

            binding.waypointNameEditText.setText( waypoint.name )
            setType( waypoint.type )
        }
        else if ( creatingNewWaypoint != null && creatingNewWaypoint )
        {
            val latitude = arguments?.getString( "latitude" )
            val longitude = arguments?.getString( "longitude" )
            val newName = R.string.sampleWaypointName

            if ( id != null && latitude != null && longitude != null )
            {
                binding.waypointId.text = id.toString()

                binding.latitudeValueEditText.setText( latitude.toString() )
                binding.longitudeValueEditText.setText( longitude.toString() )

                binding.waypointNameEditText.setText( newName )
            }
        }
    }
    /**
     * Returns true if both values are numbers, false otherwise.
     */
    private fun allowedToUpdate( newLatitude : String, newLongitude : String ) : Boolean
    {
        return viewModel.isNumber( newLatitude ) && viewModel.isNumber( newLongitude )
    }
    /**
     * Updates a waypoint if the latitude and longitude values are correct.
     */
    private fun updateExistingWaypoint( waypointId : Int )
    {
        val newLatitude = binding.latitudeValueEditText.text.toString()
        val newLongitude = binding.longitudeValueEditText.text.toString()
        val waypointName = binding.waypointNameEditText.text.toString()
        val waypointType = binding.waypointTypeTextView.text.toString()
        if ( allowedToUpdate( newLatitude, newLongitude ) ) viewModel.updateWaypoint( waypointId, LatLng( newLatitude.toDouble(), newLongitude.toDouble() ), waypointName, waypointType, "cat.png" )
    }
    /**
     * Adds a new waypoint.
     */
    private fun addNewWaypoint( latitude : String, longitude : String )
    {
        val waypointName = binding.waypointNameEditText.text.toString()
        val waypointType = binding.waypointTypeTextView.text.toString()
        viewModel.addWaypoint( LatLng( latitude.toDouble(), longitude.toDouble() ), waypointName, waypointType, "cat.png" )
    }
    /**
     * After retrieving the arguments, adds a new waypoint or updates an existing one.
     */
    private fun saveChanges()
    {
        val creatingNewWaypoint = arguments?.getBoolean( "creatingNewWaypoint" )

        val waypointId = arguments?.getInt( "waypointId" )
        val latitude = arguments?.getString( "latitude" )
        val longitude = arguments?.getString( "longitude" )

        if ( creatingNewWaypoint != null && creatingNewWaypoint && latitude != null && longitude != null ) addNewWaypoint( latitude, longitude )
        else if ( waypointId != null ) updateExistingWaypoint( waypointId )
    }
    /**
     * Sets on click events to save and discard changes.
     */
    private fun setOnClickListeners()
    {
        binding.discardChangesButton.setOnClickListener {

            findNavController().navigate( R.id.returnToWaypointList )
        }

        binding.saveChangesButton.setOnClickListener {

            saveChanges()

            findNavController().navigate( R.id.returnToWaypointList )
        }
    }
    /**
     * Updates the layouts setting the selected waypoint data if any and sets on click listeners to save and discard changes.
     */
    override fun onViewCreated( view : View, savedInstanceState : Bundle? )
    {
        super.onViewCreated( view, savedInstanceState )

        initializeWaypointData()

        setOnClickListeners()
    }
    /**
     * Sets the WaypointDetailsFragmentBinding.
     */
    override fun onCreateView( layoutInflater : LayoutInflater, viewGroup : ViewGroup?, bundle : Bundle? ) : View
    {
        binding = WaypointDetailsFragmentBinding.inflate( layoutInflater )
        return binding.root
    }
}