package com.example.minimap.fragment

import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.minimap.R
import com.example.minimap.databinding.LoginFragmentBinding
import com.example.minimap.viewModel.ViewModel


class LoginFragment : Fragment()
{
    private lateinit var binding : LoginFragmentBinding
    private val viewModel : ViewModel by activityViewModels()
    /**
     * Sets on click listeners to go to the sign up fragment and to go to the waypoint list fragment.
     */
    override fun onViewCreated( view : View, savedInstanceState : Bundle? )
    {
        super.onViewCreated( view, savedInstanceState )

        binding.google.setImageResource( R.drawable.google )

        binding.signUpButton.setOnClickListener {

            findNavController().navigate( R.id.toSignUp )
        }

        binding.loginButton.setOnClickListener {

            findNavController().navigate( R.id.showWaypoints )
        }
    }
    /**
     * Sets the LoginFragmentBinding.
     */
    override fun onCreateView( layoutInflater : LayoutInflater, viewGroup : ViewGroup?, bundle : Bundle? ) : View
    {
        binding = LoginFragmentBinding.inflate( layoutInflater )
        return binding.root
    }
}