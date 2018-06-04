package com.example.wilson.examen_moviles.baseDeDatos


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.wilson.examen_moviles.entidades.Materia



class ServicioMateria {
    companion object {
        val BDD_NOMBRE = "examen"
        val BDD_TABLA_MATERIA_NOMBRE = "materia"
        val BDD_TABLA_MATERIA_CAMPO_IDMATERIA = "idMateria"
        val BDD_TABLA_MATERIA_CAMPO_CODIGO = "codigo"
        val BDD_TABLA_MATERIA_CAMPO_DESCRIPCION = "descripcion"
        val BDD_TABLA_MATERIA_CAMPO_ACTIVO = "activo"
        val BDD_TABLA_MATERIA_CAMPO_FECHACREACION = "fechaCreacion"
        val BDD_TABLA_MATERIA_CAMPO_NUMEROHORASPORSEMANA = "numerohorasporsemana"
        val BDD_TABLA_MATERIA_CAMPO_ESTUDIANTE_IDESTUDIANTE = "idEstudiante"

    }
}

class DbHandlerMateria(context: Context) : SQLiteOpenHelper(context, ServicioMateria.BDD_NOMBRE, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarMateria(nombreMateria: Int, codigo: String, descripcion: String, activo: String, fechaCreacion: String, numeroHoras: Int, idEstudiante: Int) {


        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(ServicioMateria.BDD_TABLA_MATERIA_CAMPO_IDMATERIA, nombreMateria)
        cv.put(ServicioMateria.BDD_TABLA_MATERIA_CAMPO_CODIGO, codigo)
        cv.put(ServicioMateria.BDD_TABLA_MATERIA_CAMPO_DESCRIPCION, descripcion)
        cv.put(ServicioMateria.BDD_TABLA_MATERIA_CAMPO_ACTIVO, activo)
        cv.put(ServicioMateria.BDD_TABLA_MATERIA_CAMPO_FECHACREACION, fechaCreacion)
        cv.put(ServicioMateria.BDD_TABLA_MATERIA_CAMPO_NUMEROHORASPORSEMANA, numeroHoras)
        cv.put(ServicioMateria.BDD_TABLA_MATERIA_CAMPO_ESTUDIANTE_IDESTUDIANTE, idEstudiante)

        Log.i("consulta-materias","llegue")


        val resultado = dbWriteable.insert(ServicioMateria.BDD_TABLA_MATERIA_NOMBRE, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()

    }

    fun leerDatos(consulta:String):ArrayList<Materia> {
        val dbReadable = readableDatabase



        val materias : ArrayList<Materia> = ArrayList()
        val query = consulta

        Log.i("consulta",query)


        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {

                materias.add(Materia(resultado.getString(0).toInt(),
                        resultado.getString(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getString(5).toInt(),
                        resultado.getString(6).toInt()))

            } while (resultado.moveToNext())
        }
        resultado.close()
        dbReadable.close()
        return materias
    }

}