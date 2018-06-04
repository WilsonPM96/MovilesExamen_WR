package com.example.wilson.examen_moviles.baseDeDatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.wilson.examen_moviles.entidades.Estudiante


class ServicioEstudiante {
    companion object {
        val BDD_NOMBRE = "examen"
        val BDD_TABLA_ESTUDIANTE_NOMBRE = "estudiante"
        val BDD_TABLA_ESTUDIANTE_CAMPO_IDESTUDIANTE = "idEstudiante"
        val BDD_TABLA_ESTUDIANTE_CAMPO_NOMBRES = "nombres"
        val BDD_TABLA_ESTUDIANTE_CAMPO_APELLIDOS = "apellidos"
        val BDD_TABLA_ESTUDIANTE_CAMPO_FECHANACIMIENTO = "fechaNacimiento"
        val BDD_TABLA_ESTUDIANTE_CAMPO_SEMESTREACTUAL = "semestreActual"
        val BDD_TABLA_ESTUDIANTE_CAMPO_GRADUADO = "graduado"

    }
}

class DbHandlerEstudiante(context: Context) : SQLiteOpenHelper(context, ServicioEstudiante.BDD_NOMBRE, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE+"( "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_IDESTUDIANTE+
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_NOMBRES+" VARCHAR(50), "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_APELLIDOS+" VARCHAR(50), "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_FECHANACIMIENTO+" VARCHAR(50), "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_SEMESTREACTUAL+" INTEGER, "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_GRADUADO+" VARCHAR(10))"

        Log.i("sql",createTableSQL)

        val createTableSQLMateria =  "CREATE TABLE "+ServicioMateria.BDD_TABLA_MATERIA_NOMBRE+"( "+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_IDMATERIA+
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_CODIGO+" VARCHAR(50), "+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_DESCRIPCION+
                " INTEGER, "+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_ACTIVO+" VARCHAR(50), "+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_FECHACREACION+" VARCHAR(50), "+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_NUMEROHORASPORSEMANA+" INTEGER, "+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_ESTUDIANTE_IDESTUDIANTE+" INTEGER, FOREIGN KEY ("+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_ESTUDIANTE_IDESTUDIANTE+") REFERENCES estudiante(idEstudiante) )"
        Log.i("sql",createTableSQLMateria)

        db?.execSQL(createTableSQL)
        db?.execSQL(createTableSQLMateria)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarEstudiante(nombres: String, apellidos: String, fechaNacimiento: String, semestreActual: Int, graduado: String) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        Log.i("llegar", "llegue")

        cv.put(ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_NOMBRES, nombres)
        cv.put(ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_APELLIDOS, apellidos)
        cv.put(ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_FECHANACIMIENTO, fechaNacimiento)
        cv.put(ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_SEMESTREACTUAL, semestreActual)
        cv.put(ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_GRADUADO, graduado)



        val resultado = dbWriteable.insert(ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()

    }

    fun eliminarEstudiante(idEstudiante:Int){

        val dbWriteable = writableDatabase

        val queryBorrarMaterias = "DELETE FROM "+ServicioMateria.BDD_TABLA_MATERIA_NOMBRE+
                " WHERE "+ServicioMateria.BDD_TABLA_MATERIA_NOMBRE+"."+ServicioMateria.BDD_TABLA_MATERIA_CAMPO_ESTUDIANTE_IDESTUDIANTE+ " = "+idEstudiante

        dbWriteable.execSQL(queryBorrarMaterias)
        Log.i("delete",queryBorrarMaterias)

        val queryBorrarEstudiantes = "DELETE FROM "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE+" WHERE "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_IDESTUDIANTE+"="+idEstudiante

        dbWriteable.execSQL(queryBorrarEstudiantes)
        Log.i("delete",queryBorrarEstudiantes)

        dbWriteable.close()

    }

    fun updateEstudiante(idEstudiante: Int, nombres: String, apellidos: String, fechaNacimiento: String, semestreActual: Int, graduado: String) {
        val dbWriteable = writableDatabase

        val queryUpdateEsudiante = "UPDATE "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_NOMBRE+"\n" +
                "SET "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_NOMBRES+" = '"+nombres+"',\n" +
                "\t"+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_APELLIDOS+" = '"+apellidos+"',\n" +
                "\t"+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_FECHANACIMIENTO+" = '"+fechaNacimiento+"',\n" +
                "\t"+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_SEMESTREACTUAL+" = "+semestreActual+",\n" +
                "\t"+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_GRADUADO+" = '"+graduado+"'\n" +
                "WHERE "+ServicioEstudiante.BDD_TABLA_ESTUDIANTE_CAMPO_IDESTUDIANTE+" = "+idEstudiante

        dbWriteable.execSQL(queryUpdateEsudiante)
        Log.i("update", queryUpdateEsudiante)

        dbWriteable.close()

    }

    fun leerDatos(consulta:String):ArrayList<Estudiante> {
        val dbReadable = readableDatabase

        val estudiantes : ArrayList<Estudiante> = ArrayList()
        val query = consulta



        val resultado = dbReadable.rawQuery(query, null)
        if (resultado.moveToFirst()) {
            do {
                estudiantes.add(Estudiante(resultado.getString(0).toInt(),
                        resultado.getString(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getInt(4),
                        resultado.getString(5)))

            } while (resultado.moveToNext())
        }
        resultado.close()
        dbReadable.close()
        return estudiantes
    }

}