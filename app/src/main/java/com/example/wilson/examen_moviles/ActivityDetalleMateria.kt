package com.example.wilson.examen_moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.wilson.examen_moviles.entidades.Materia

class ActivityDetalleMateria : AppCompatActivity() {

    lateinit var materiaSeleccionada:Materia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_materia)

        materiaSeleccionada = intent.getParcelableExtra("materia-seleccionada")

        llenarDetalle()


    }

    private fun llenarDetalle() {

        val nombreMateria: TextView = findViewById(R.id.textView_nombreMateria_detalle)
        val codigo: TextView = findViewById(R.id.textView_codigo_detalle)
        val descripcion : TextView = findViewById(R.id.textView_descripcion_materia)
        val activo: TextView = findViewById(R.id.textView_fechaCreacion_Materia)
        val numeroHorasporSemana: TextView = findViewById(R.id.textView_horas_Materia)

        nombreMateria.text = materiaSeleccionada.idMateria.toString()
        codigo.text = materiaSeleccionada.codigo
        descripcion.text = materiaSeleccionada.descripcion
        activo.text=materiaSeleccionada.activo
        numeroHorasporSemana.text=materiaSeleccionada.numeroHorasPorSemana.toString()


    }
}

