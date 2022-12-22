package com.example.minimap.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.minimap.R
import com.example.minimap.databinding.MapFragmentBinding
import com.example.minimap.viewModel.ViewModel
import com.example.minimap.fragment.MapFragmentDirections
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

const val REQUEST_CODE_LOCATION = 100


class MapFragment : Fragment(), OnMapReadyCallback
{
    lateinit var map : GoogleMap
    private lateinit var binding : MapFragmentBinding
    private val viewModel : ViewModel by activityViewModels()
    /**
     * Initializes the map with its layout id.
     */
    private fun createMap()
    {
        val mapFragment = childFragmentManager.findFragmentById( R.id.map ) as SupportMapFragment?
        mapFragment?.getMapAsync( this )
    }
    /**
     * Creates a waypoint.
     */
    private fun createMarker( latLng : LatLng, waypointName : String )
    {
        val myMarker = MarkerOptions().position( latLng ).title( waypointName ).draggable( true )
        map.addMarker( myMarker )
        map.animateCamera( CameraUpdateFactory.newLatLngZoom( latLng, 18f ), 5000, null )
    }
    /**
     * Loads all markers.
     */
    private fun loadAllMarkers()
    {
        viewModel.getUnfilteredWaypoints().forEach {

            createMarker( it.position, it.name )
        }
    }
    /**
     * Returns true if the location is allowed, false otherwise.
     */
    private fun isLocationPermissionGranted() : Boolean
    {
        return ContextCompat.checkSelfPermission( requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    /**
     * Asks the user for the location permission.
     */
    private fun requestLocationPermission()
    {
        if( ActivityCompat.shouldShowRequestPermissionRationale( requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) )
        {
            Toast.makeText( requireContext(), "Go to the application permission screen and allow location", Toast.LENGTH_SHORT ).show()
        }
        else
        {
            ActivityCompat.requestPermissions( requireActivity(), arrayOf( Manifest.permission.ACCESS_FINE_LOCATION ), REQUEST_CODE_LOCATION )
        }
    }
    /**
     * Executes when the permission request result is processed, just after requestLocationPermission().
     */
    override fun onRequestPermissionsResult( requestCode : Int, permissions : Array<out String>, grantResults : IntArray )
    {
        when ( requestCode )
        {
            REQUEST_CODE_LOCATION -> if ( grantResults.isNotEmpty() && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED )
            {
                if ( ActivityCompat.checkSelfPermission( this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
                { return }
                map.isMyLocationEnabled = true
            }
            else
            {
                Toast.makeText( requireContext(), "Accept location permission", Toast.LENGTH_SHORT ).show()
            }
        }
    }
    /**
     * Sets location permission to true.
     */
    private fun enableLocation()
    {
        if ( !::map.isInitialized ) return
        if ( isLocationPermissionGranted() )
        {
            if ( ActivityCompat.checkSelfPermission( this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
            { return }
            map.isMyLocationEnabled = true
        }
        else
        {
            requestLocationPermission()
        }
    }
    /**
     * Executes every time the map is loaded.
     */
    override fun onMapReady( googleMap : GoogleMap )
    {
        map = googleMap
        enableLocation()
        map.setOnMapClickListener {

            val action = MapFragmentDirections.createWaypoint( viewModel.getNewId(), it.latitude.toString(), it.longitude.toString(), true )

            findNavController().navigate( action )
        }
    }
    /**
     * Executes every time the map is shown, checks the location permissions are allowed.
     */
    override fun onResume()
    {
        super.onResume()
        if ( !::map.isInitialized ) return
        if ( !isLocationPermissionGranted() )
        {
            if ( ActivityCompat.checkSelfPermission( this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
            { return }
            map.isMyLocationEnabled = false
            Toast.makeText( requireContext(), "Accept location permission", Toast.LENGTH_SHORT ).show()
        }
        loadAllMarkers()
    }
    /**
     * Sets the MapFragmentBinding and creates the map.
     */
    override fun onCreateView( layoutInflater : LayoutInflater, viewGroup : ViewGroup?, bundle : Bundle? ) : View
    {
        binding = MapFragmentBinding.inflate( layoutInflater )
        createMap()
        return binding.root
    }
}