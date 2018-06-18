package com.example.wilson.examen_moviles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.util.*

class HttpFuelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http_fuel)

        "http://172.31.104.12:1337/Estudiante/1"
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-ejemplo", "Error ${ex.response}")
                        }
                        is Result.Success -> {
                            val jsonStringEstudiante = result.get()
                            Log.i("http-ejemplo", "Exito ${jsonStringEstudiante}")

                            val estudiante: Estudiante? = Klaxon()
                                    .parse<Estudiante>(jsonStringEstudiante)

                            if (estudiante != null) {
                                Log.i("http-ejemplo", "Nombre: ${estudiante.nombres}")
                                Log.i("http-ejemplo", "Apellido: ${estudiante.apellidos}")
                                Log.i("http-ejemplo", "Id: ${estudiante.id}")
                                Log.i("http-ejemplo", "Fecha Nacimiento: ${estudiante.fechaNacimiento}")
                                Log.i("http-ejemplo", "Semestre Actual: ${estudiante.semestreActual}")
                                Log.i("http-ejemplo", "Graduado: ${estudiante.graduado}")
                                Log.i("http-ejemplo", "Creado: ${estudiante.createdAtDate}")
                                Log.i("http-ejemplo", "Actualizado: ${estudiante.updatedAtDate}")

                                estudiante.materias.forEach { materia: Materia ->
                                    Log.i("http-ejemplo", "Codigo ${materia.codigo}")
                                    Log.i("http-ejemplo", "Descripcion ${materia.descripcion}")
                                    Log.i("http-ejemplo", "Activo ${materia.activo}")
                                }



                            } else {
                                Log.i("http-ejemplo", "Estudiante nulo")
                            }


                        }
                    }
                }
    }
}

class Estudiante(var idEstudiante: Int,
                 var nombres: String,
                 var apellidos: String,
                 var fechaNacimiento: String,
                 var semestreActual: Int,
                 var graduado: String,
                 var createdAt: Long,
                 var updatedAt: Long,
                 var id: Int,
                 var materias: List<Materia>) {
    var createdAtDate = Date(updatedAt)
    var updatedAtDate = Date(createdAt)


}

class Materia(var idMateria: Int,
              var codigo: String,
              var descripcion: Int,
              var activo: String,
              var fechaCreacion: String,
              var numeroHorasPorSemana: Int,
              var createdAt: Long,
              var updatedAt: Long,
              var id: Int,
              var estudianteId: Int) {
    var createdAtDate = Date(updatedAt)
    var updatedAtDate = Date(createdAt)
}

