package com.example.wilson.examen_moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_crear.setOnClickListener {
            view: View ->  irAActividadCrear()
        }

        btn_listar.setOnClickListener{
            view: View -> irAActividadListar()
        }
        btn_fuel.setOnClickListener{
            view: View -> iraActividadFuel()
        }

        boton_mapa.setOnClickListener{
            view: View -> iraActividadMapa()
        }
    }

    private fun irAActividadListar() {
        intent = Intent(this, ActividadListar::class.java)

        startActivity(intent)
    }

    private fun irAActividadCrear() {
        intent = Intent(this, ActividadCrear::class.java)
        startActivity(intent)
    }
    fun iraActividadFuel() {
        intent = Intent(this, HttpFuelActivity::class.java)
        startActivity(intent)
    }

    fun iraActividadMapa() {
        intent = Intent(this, GoogleMapsActivity::class.java)
        startActivity(intent)
    }
}