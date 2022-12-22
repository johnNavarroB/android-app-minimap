package com.example.minimap.fragment

import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.minimap.R
import com.example.minimap.databinding.SignUpFragmentBinding
import com.example.minimap.viewModel.ViewModel


class SignUpFragment : Fragment()
{
    private lateinit var binding : SignUpFragmentBinding
    private val viewModel : ViewModel by activityViewModels()
    /**
     * Sets on click listeners to go back to the login fragment and to go to the waypoint list fragment.
     */
    override fun onViewCreated( view : View, savedInstanceState : Bundle? )
    {
        super.onViewCreated( view, savedInstanceState )

        binding.backToLogin.setOnClickListener {

            findNavController().navigate( R.id.backToLogin )
        }

        binding.signUp.setOnClickListener {

            findNavController().navigate( R.id.showWaypoints )
        }
    }
    /**
     * Sets the SignUpFragmentBinding.
     */
    override fun onCreateView( layoutInflater : LayoutInflater, viewGroup : ViewGroup?, bundle : Bundle? ) : View
    {
        binding = SignUpFragmentBinding.inflate( layoutInflater )
        return binding.root
    }
}