package com.example.minimap.viewModel

import com.example.minimap.model.Waypoint
import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.math.round


class ViewModel : androidx.lifecycle.ViewModel()
{   // The original list of waypoints, they are added to this list.
    private val waypoints = mutableListOf<Waypoint>()
    // A list of waypoints that are constantly filtered when the user types in the search box.
    private val filteredWaypoints = mutableListOf<Waypoint>()
    /**
     * Gets the waypoints that have been filtered.
     */
    fun getWaypoints() = filteredWaypoints
    /**
     * Gets the waypoint list.
     */
    fun getUnfilteredWaypoints() = filteredWaypoints
    /**
     * Gets the size of the waypoints collection.
     */
    fun getNewId() = waypoints.size
    /**
     * Updates a waypoint from the waypoints list.
     */
    fun updateWaypoint( waypointId : Int, position : LatLng, name : String, type : String, image : String  )
    {
        val waypoint = filteredWaypoints.find { it.id == waypointId }
        if ( waypoint != null ) filteredWaypoints[ waypointId ] = Waypoint( waypointId, position, name, type, image )
        waypoints[ waypointId ] = Waypoint( waypointId, position, name, type, image )
    }
    /**
     * Adds a waypoint to the waypoints list.
     */
    fun addWaypoint( position : LatLng, name : String, type : String, image : String  )
    {
        filteredWaypoints.add( Waypoint( waypoints.size, position, name, type, image ) )
        waypoints.add( Waypoint( waypoints.size, position, name, type, image ) )
    }
    /**
     * Returns true if the string passed through parameter matches with the longitude of a waypoint.
     */
    private fun longitudeMatches( waypoint : Waypoint, search : String ) : Boolean
    {
        return isNumber( search ) && roundToTwoDecimals( waypoint.position.longitude ).toString().contains( search )
    }
    /**
     * Returns true if the string passed through parameter matches with the latitude of a waypoint.
     */
    private fun latitudeMatches( waypoint : Waypoint, search : String ) : Boolean
    {
        return isNumber( search ) && roundToTwoDecimals( waypoint.position.latitude ).toString().contains( search )
    }
    /**
     * Rounds a value to two decimals.
     */
    fun roundToTwoDecimals( double : Double ) : Double
    {
        return round( double * 100 ) / 100
    }
    /**
     * Returns true if the string passed through parameter matches with the name of a waypoint.
     */
    private fun nameMatches( waypoint : Waypoint, search : String ) : Boolean
    {
        return waypoint.name.lowercase( Locale.getDefault() ).contains( search.lowercase( Locale.getDefault() ) )
    }
    /**
     * Returns true if the string passed through parameter matches with the id of a waypoint.
     */
    private fun idMatches( waypoint : Waypoint, search : String ) : Boolean
    {
        return isNumber( search ) && waypoint.id.toDouble() == search.toDouble()
    }
    /**
     * Returns true if a string is a number, false otherwise.
     */
    fun isNumber( string : String ) : Boolean
    {
        return string.matches( "-?\\d+(\\.\\d+)?".toRegex() )
    }
    /**
     * Adds in the filtered waypoint list the waypoints that match with the string passed through parameter.
     */
    fun filter( search : String )
    {
        filteredWaypoints.clear()

        waypoints.map {

            if ( idMatches( it, search ) || nameMatches( it, search ) || latitudeMatches( it, search ) || longitudeMatches( it, search ) ) filteredWaypoints.add( it )
        }
    }
}