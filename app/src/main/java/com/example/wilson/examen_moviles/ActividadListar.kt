package com.example.wilson.examen_moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerEstudiante
import com.example.wilson.examen_moviles.baseDeDatos.ServicioEstudiante
import com.example.wilson.examen_moviles.entidades.Estudiante
import kotlinx.android.synthetic.main.activity_actividad_listar.*
import kotlin.collections.ArrayList

class ActividadListar : AppCompatActivity() {

    lateinit var recyclerEstudiante:RecyclerView
    lateinit var listaEstudiante: ArrayList<Estudiante>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_listar)

        listaEstudiante = ArrayList()

        recyclerEstudiante=findViewById(R.id.recycler_view_estudiante)
        recyclerEstudiante.layoutManager= LinearLayoutManager(this)

        llenarPersonajes()

        val adaptador = AdaptadorEstudiante(listaEstudiante,this)
        recyclerEstudiante.adapter = adaptador

        // registerForContextMenu(recyclerActor);

        btn_actualizar_actividad.setOnClickListener {
            view: View? -> actualizarActividad()
        }


    }

    private fun actualizarActividad() {
        finish()
        startActivity(intent)
    }

    private fun llenarPersonajes() {

        //Aqui se consulta a la base de datos para obtener todos los actores
        val dbHandlerEstudiante =  DbHandlerEstudiante(this)
        listaEstudiante= dbHandlerEstudiante.leerDatos("SELECT * FROM "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE)

    }
}

