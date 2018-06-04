package com.example.wilson.examen_moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerMateria
import com.example.wilson.examen_moviles.baseDeDatos.ServicioEstudiante
import com.example.wilson.examen_moviles.baseDeDatos.ServicioMateria
import com.example.wilson.examen_moviles.entidades.Estudiante
import kotlinx.android.synthetic.main.activity_detalle.*
import com.example.wilson.examen_moviles.entidades.Materia


class DetalleActivity : AppCompatActivity() {

    lateinit var recyclerMaterias: RecyclerView
    lateinit var listaMaterias: ArrayList<Materia>

    lateinit var estudiante : Estudiante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        estudiante = intent.getParcelableExtra("estudiante-seleccionado")
        llenarDetalle(estudiante)

        listaMaterias = ArrayList()
        recyclerMaterias=findViewById(R.id.recyclerView_materias)
        recyclerMaterias.layoutManager= LinearLayoutManager(this)

        llenarMaterias(estudiante)

        val adaptadorMateria = AdaptadorDetalle(listaMaterias,this)
        recyclerMaterias.adapter = adaptadorMateria


        registerForContextMenu(recyclerView_materias)



        btn_nuevo.setOnClickListener{
            view: View? -> irAActividadNuevaMateria()
        }


    }

    private fun irAActividadNuevaMateria() {
        val intent = Intent(this, CrearMateriaActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun llenarMaterias(estudiante: Estudiante) {

        val dbHandlerMateria = DbHandlerMateria(this)

        listaMaterias= dbHandlerMateria.leerDatos("SELECT * " +
                "FROM "+ServicioMateria.BDD_TABLA_MATERIA_NOMBRE+", "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE+
                " WHERE "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE+"."+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_IDESTUDIANTE+
                " = "+ServicioMateria.BDD_TABLA_MATERIA_NOMBRE+"."+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_ESTUDIANTE_IDESTUDIANTE+" " +
                "AND "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE+"."+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_IDESTUDIANTE+"="+estudiante.idEstudiante)





    }

    private fun llenarDetalle(est: Estudiante?) {

        txt_nombreEstudiante.text = est?.nombres
        txt_apellidoEstudiante.text = est?.apellidos
        txt_semestreActual.text=est?.semestreActual.toString()

        txt_fechaNac.text=est?.fechaNacimiento

        if(est?.graduado=="Graduado"){
            txt_graduadoEstudiante.text=this.getString(R.string.elEstudianteEstaGraduado)
        }else{
            txt_graduadoEstudiante.text=this.getString(R.string.elEstudianteNoestaGraduado)
        }


    }

}

