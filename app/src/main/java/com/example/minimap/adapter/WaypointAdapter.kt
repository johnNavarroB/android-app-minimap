package com.example.minimap.adapter

import kotlin.math.round
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.minimap.R
import com.example.minimap.databinding.WaypointViewHolderBinding
import com.example.minimap.fragment.WaypointListFragmentDirections
import com.example.minimap.model.Waypoint


class WaypointAdapter( private val waypoints : MutableList<Waypoint> ) : RecyclerView.Adapter<WaypointAdapter.WaypointAdapterViewHolder>()
{
    /**
     * Binds to the WaypointViewHolderBinding in order to have access to its views.
     */
    inner class WaypointAdapterViewHolder( view : View ) : RecyclerView.ViewHolder( view )
    {
        val binding = WaypointViewHolderBinding.bind( view )
    }
    /**
     * Returns the size of the waypoints list.
     */
    override fun getItemCount() = waypoints.size
    /**
     * Binds the "waypoint_view_holder" view to the content of each waypoint in the recycler view.
     */
    override fun onCreateViewHolder( parent : ViewGroup, viewType : Int ) : WaypointAdapterViewHolder
    {
        val view = LayoutInflater.from( parent.context ).inflate( R.layout.waypoint_view_holder, parent, false )
        return WaypointAdapterViewHolder( view )
    }
    /**
     * Sets an on click event that allows the user to access to the details fragment of a waypoint.
     */
    private fun setWaypointOnClickListener( viewHolder : WaypointAdapterViewHolder, index : Int )
    {
        viewHolder.itemView.setOnClickListener {

            val action = WaypointListFragmentDirections.waypointDetails( waypoints[ index ].id, waypoints[ index ].position.latitude.toString(), waypoints[ index ].position.longitude.toString(), false )

            Navigation.findNavController( it ).navigate( action )
        }
    }
    /**
     * Sets the name, position and the rest of the data of each waypoint in its respective view holder.
     */
    private fun initializeViewHolderData( viewHolder : WaypointAdapterViewHolder, index : Int )
    {
        val latitude = round( waypoints[ index ].position.latitude * 100 ) / 100
        val longitude = round( waypoints[ index ].position.longitude * 100 ) / 100

        val waypointName = "Nº ${waypoints[ index ].id}: ${waypoints[ index ].name}"
        val waypointLatitude = "Lat: $latitude"
        val waypointLongitude = "Long: $longitude"

        viewHolder.binding.img.setImageResource( R.drawable.image_unavailable )
        viewHolder.binding.waypointName.text = waypointName
        viewHolder.binding.latitude.text = waypointLatitude
        viewHolder.binding.longitude.text = waypointLongitude
        viewHolder.binding.type.text = waypoints[ index ].type
    }
    /**
     * Sets the data of each waypoint and an on click event.
     */
    override fun onBindViewHolder( waypointAdapterViewHolder : WaypointAdapterViewHolder, index : Int )
    {
        initializeViewHolderData( waypointAdapterViewHolder, index )

        setWaypointOnClickListener( waypointAdapterViewHolder, index )
    }
}