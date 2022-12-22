package com.example.minimap.fragment

import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.minimap.R
import com.example.minimap.adapter.WaypointAdapter
import com.example.minimap.databinding.WaypointListFragmentBinding
import com.example.minimap.viewModel.ViewModel


class WaypointListFragment : Fragment()
{
    private lateinit var binding : WaypointListFragmentBinding
    private val viewModel : ViewModel by activityViewModels()
    /**
     * Executes the filter method with the current input text as parameter.
     */
    private fun filter()
    {
        viewModel.filter( binding.searchWaypointEditText.text.toString() )
    }
    /**
     * Sets an event to add a waypoint and filter the waypoints on the recycler view.
     */
    private fun setListeners()
    {
        binding.addWaypoint.setOnClickListener {

            findNavController().navigate( R.id.newWaypoint )
        }

        binding.searchWaypointEditText.addTextChangedListener {

            filter()
            updateWaypoints()
        }
    }
    /**
     * Sets the waypoint list.
     */
    private fun updateWaypoints()
    {
        binding.recyclerView.adapter = WaypointAdapter( viewModel.getWaypoints() )
    }
    /**
     * Sets the adapter and sets a text changed listener to filter the waypoints of the recycler view.
     */
    override fun onViewCreated( view : View, savedInstanceState : Bundle? )
    {
        super.onViewCreated( view, savedInstanceState )

        updateWaypoints()

        setListeners()
    }
    /**
     * Sets the WaypointListFragmentBinding.
     */
    override fun onCreateView( layoutInflater : LayoutInflater, viewGroup : ViewGroup?, bundle : Bundle? ) : View
    {
        binding = WaypointListFragmentBinding.inflate( layoutInflater )
        return binding.root
    }
}