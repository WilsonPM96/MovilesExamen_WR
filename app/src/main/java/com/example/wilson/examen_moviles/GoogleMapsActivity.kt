package com.example.wilson.examen_moviles

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_google_maps.*
import kotlin.collections.ArrayList

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener {

    var usuarioTienePermiososLocalizacion = false

    var mMarkerArray = ArrayList<Marker>()

    val zoom = 17f

    val materiaLatLang = LatLng(-0.21543, -78.42345)

    val materia2LatLang = LatLng(-0.2101171,-78.4884667)

    val materia3LatLang = LatLng(-0.209133, -78.486992)

    private val materia: CameraPosition = CameraPosition.Builder()
            .target(materiaLatLang)
            .zoom(zoom)
            .build()

    private val materia2: CameraPosition = CameraPosition.Builder()
            .target(materia2LatLang)
            .zoom(zoom)
            .build()

    private val materia3: CameraPosition = CameraPosition.Builder()
            .target(materia3LatLang)
            .zoom(zoom)
            .build()


    override fun onCameraMoveStarted(p0: Int) {

    }


    override fun onCameraMove() {

    }

    override fun onCameraMoveCanceled() {

    }

    override fun onCameraIdle() {

    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        solicitarPermisosLocalizacion()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        with(googleMap) {


            button_materia1.setOnClickListener { v ->
                anadirMarcador(materiaLatLang, "Marcador Materia 1")


                moverCamaraPorPosicion(materia)
            }

            button_materia2.setOnClickListener{ v ->
                anadirMarcador(materia2LatLang, "Marcador Materia 2")


                moverCamaraPorPosicion(materia2)
            }

            button_materia3.setOnClickListener{ v ->
                anadirMarcador(materia3LatLang, "Marcador Materia 2")


                moverCamaraPorPosicion(materia3)
            }



        }
        // Add a marker in Sydney and move the camera
    }


    fun solicitarPermisosLocalizacion() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            usuarioTienePermiososLocalizacion = true
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun establecerSettings(googleMap: GoogleMap) {
        with(googleMap) {
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    private fun establecerListeners(googleMap: GoogleMap) {
        with(googleMap) {
            setOnCameraIdleListener(this@GoogleMapsActivity)
            setOnCameraMoveStartedListener(this@GoogleMapsActivity)
            setOnCameraMoveListener(this@GoogleMapsActivity)
            setOnCameraMoveCanceledListener(this@GoogleMapsActivity)

        }
    }

    private fun anadirMarcador(latitudLongitud: LatLng, titulo: String) {
        mMarkerArray.forEach { marker: Marker ->
            marker.remove()
        }
        mMarkerArray = ArrayList<Marker>()
        val marker = mMap.addMarker(MarkerOptions().position(latitudLongitud).title(titulo))
        mMarkerArray.add(marker)
        Log.i("map-wilson", "$mMarkerArray")
    }

    private fun moverCamaraPorLatLongZoom(latitudLongitud: LatLng, zoom: Float) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latitudLongitud, zoom))
    }

    private fun moverCamaraPorPosicion(posicionCamara: CameraPosition) {
        changeCamera(CameraUpdateFactory.newCameraPosition(posicionCamara))
    }

    private fun changeCamera(update: CameraUpdate, callback: GoogleMap.CancelableCallback? = null) {
//        if (animateToggle.isChecked) {
//            if (customDurationToggle.isChecked) {
//                // The duration must be strictly positive so we make it at least 1.
//                map.animateCamera(update, Math.max(customDurationBar.progress, 1), callback)
//            } else {
//                map.animateCamera(update, callback)
//            }
//        } else {
        mMap.moveCamera(update)
//        }
    }
}


