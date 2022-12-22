package com.example.minimap.model

import com.google.android.gms.maps.model.LatLng
/**
 * Basic data model of a waypoint, saves its position, name, type and an image of it.
 */
data class Waypoint( val id : Int, val position : LatLng, val name : String, val type : String, val image : String )