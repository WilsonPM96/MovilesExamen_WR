package com.example.wilson.examen_moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerEstudiante
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerMateria
import com.example.wilson.examen_moviles.baseDeDatos.ServicioEstudiante
import com.example.wilson.examen_moviles.entidades.Estudiante
import com.example.wilson.examen_moviles.entidades.Materia
import kotlinx.android.synthetic.main.activity_crear_materia.*
import java.util.*

class CrearMateriaActivity : AppCompatActivity() {

    lateinit var arregloEstudiantes : ArrayList<Estudiante>
    lateinit var arregloNombreEstudiantes: ArrayList<String>
    var indicaEstudianteSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_materia)

        arregloEstudiantes = ArrayList()
        arregloNombreEstudiantes = ArrayList()

        arregloNombreEstudiantes = llenarSpinnerEstudiantes()


        val adaptadorSpinnerEstudiante = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arregloNombreEstudiantes)
        adaptadorSpinnerEstudiante.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_estudiantes.adapter = adaptadorSpinnerEstudiante

        spinner_estudiantes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //nada
            }

            override fun onItemSelected(adaptador: AdapterView<*>?, view: View?, posicion: Int, id: Long) {
                indicaEstudianteSeleccionado = posicion

            }

        }

        btn_crearPelicula.setOnClickListener {
            view: View? -> registrarMateria(indicaEstudianteSeleccionado)
        }

    }

    private fun registrarMateria(indicaEstudianteSeleccionado: Int) {

        val idEstudiante = arregloEstudiantes[indicaEstudianteSeleccionado].idEstudiante
        val activo = arregloEstudiantes[indicaEstudianteSeleccionado].semestreActual

        Log.i("id-estudiante",idEstudiante.toString())

        val dbHandlerMateria = DbHandlerMateria(this)

        val nombreMateria:EditText = findViewById(R.id.editText_nombreMateria)
        val codigoMateria:EditText = findViewById(R.id.editText_codigo)
        val descripcionMateria:EditText = findViewById(R.id.editText_descripcion)
        val horasMateria:EditText = findViewById(R.id.editText_numeroHoras)
        val fechaCreacion = Calendar.getInstance()

        val materia = Materia(nombreMateria.text.toString().toInt(),
                codigoMateria.text.toString(),
                descripcionMateria.text.toString(),
                activo.toString(),
                fechaCreacion.toString(),
                horasMateria.text.toString().toInt(),
                idEstudiante)

        dbHandlerMateria.insertarMateria(materia.idMateria,
                materia.codigo,
                materia.descripcion,
                materia.activo,
                materia.fechaCreacion,
                materia.numeroHorasPorSemana,
                materia.estudianteId)

        Log.i("insertar-materia","Se inserto exitosamente")

        val intent = Intent(this, ActividadListar::class.java)
        finish()
        startActivity(intent)


    }

    private fun llenarSpinnerEstudiantes() :ArrayList<String> {

        val arregloNombreEstudiantes: ArrayList<String>
        arregloNombreEstudiantes= ArrayList()

        val dbHandlerEstudiante = DbHandlerEstudiante(this)

        arregloEstudiantes = dbHandlerEstudiante.leerDatos("SELECT * FROM "+ ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE)

        for(i in arregloEstudiantes.indices){

            arregloNombreEstudiantes.add(arregloEstudiantes[i].nombres+" "+arregloEstudiantes[i].apellidos)
        }

        return arregloNombreEstudiantes





    }
}

