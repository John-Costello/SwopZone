package org.wit.swopzone.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.swopzone.R
import org.wit.swopzone.models.Location

class MapsActivity: AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener,
                                         GoogleMap.OnMarkerClickListener{

     private lateinit var map: GoogleMap
     var location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        location=intent.extras?.getParcelable<Location>("location_offer")!!
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap){
        map = googleMap

        val loc = LatLng(location.lat, location.lng)

        val options = MarkerOptions()
            .title("SwopZone")
            .snippet("GPS: "+loc.toString())
            .draggable(true)
            .position(loc)

        map.addMarker(options)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        map.setOnMarkerDragListener(this)
        map.setOnMarkerClickListener(this)
    }

    override fun onMarkerDragStart(marker: Marker){
    }

    override fun onMarkerDrag(marker: Marker){
    }

    override fun onMarkerDragEnd(marker: Marker){
        location.lat=marker.position.latitude
        location.lng=marker.position.longitude
        location.zoom=map.cameraPosition.zoom
    }

    override fun onBackPressed(){
        val resultIntent = Intent()
        resultIntent.putExtra("marker_location",location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }

    override fun onMarkerClick(marker: Marker): Boolean{
        val loc = LatLng(location.lat,location.lng)
        marker.setSnippet("GPS: "+loc.toString())
        return false
    }
}