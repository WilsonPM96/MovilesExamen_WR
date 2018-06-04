package com.example.wilson.examen_moviles

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerEstudiante
import com.example.wilson.examen_moviles.entidades.Estudiante
import kotlinx.android.synthetic.main.activity_actividad_crear.*
import java.util.*

class ActivityEditarEstudiante : AppCompatActivity(), View.OnClickListener {
    lateinit var estudiante : Estudiante

    var c = Calendar.getInstance()

    var mes = c.get(Calendar.MONTH)
    var dia = c.get(Calendar.DAY_OF_MONTH)
    var anio = c.get(Calendar.YEAR)

    lateinit var fechaNacEstudiante: EditText

    var CERO = "0"
    var BARRA = "/"

    lateinit var arregloOpciones : ArrayList<String>

    var graduado: String =""
    var idEstudiante: Int=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_crear)

        fechaNacEstudiante = findViewById(R.id.editText_fechaNac)
        fechaNacEstudiante.setOnClickListener(this)

        arregloOpciones = ArrayList()
        arregloOpciones.add(this.getString(R.string.opcionGraduadoSpinner))
        arregloOpciones.add(this.getString(R.string.opcionNoGraduadoSpinner))

        val botonEditarEstudiante:Button = findViewById(R.id.btn_RegistrarEstudiante)
        botonEditarEstudiante.text=this.getString(R.string.botonEditarEstudiante)


        estudiante = intent.getParcelableExtra("estudiante-a-editar")
        idEstudiante = estudiante.idEstudiante
        llenarInterfaz()

        val adaptadorSpinnerOpciones = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arregloOpciones)
        adaptadorSpinnerOpciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_graduado.adapter = adaptadorSpinnerOpciones


        spinner_graduado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //nada
            }

            override fun onItemSelected(adaptador: AdapterView<*>?, view: View?, posicion: Int, id: Long) {
                if(posicion==0){
                    graduado = arregloOpciones[0]
                    Log.i("Graduado",graduado)
                }else if(posicion==1){
                    graduado= arregloOpciones[1]
                    Log.i("No Graduado",graduado)
                }

            }

        }

        btn_RegistrarEstudiante.setOnClickListener {
            view: View? -> editarEstudiante()
        }


    }

    private fun editarEstudiante() {
        val dbHandlerEstudiante = DbHandlerEstudiante(this)

        val nombrEstudiante:EditText = findViewById(R.id.editTxt_nombreEstudiante)
        val apellidoEstudiante:EditText = findViewById(R.id.editTxt_apellidoEstudiante)
        val semestreActual: EditText = findViewById(R.id.editText_numeroPeliculas)
        val fechaNacimiento:EditText = findViewById(R.id.editText_fechaNac)


        val estudiante=Estudiante(idEstudiante, nombrEstudiante.text.toString(),
                apellidoEstudiante.text.toString(),
                fechaNacimiento.text.toString(),
                semestreActual.text.toString().toInt(),
                graduado)




        dbHandlerEstudiante.updateEstudiante(idEstudiante, estudiante.nombres, estudiante.apellidos, estudiante.fechaNacimiento, estudiante.semestreActual, estudiante.graduado)
        Log.i("llegareee","llegue")
        val intent = Intent (this, ActividadListar::class.java)
        finish()
        startActivity(intent)

    }

    private fun llenarInterfaz() {

        val nombreEstudiante: EditText = findViewById(R.id.editTxt_nombreEstudiante)
        val apellidoEstudiante: EditText = findViewById(R.id.editTxt_apellidoEstudiante)
        fechaNacEstudiante = findViewById(R.id.editText_fechaNac)
        // val semestreActual: EditText = findViewById(R.id.editText_numeroPeliculas)

        Log.i("interfaz","llegue")

        nombreEstudiante.setText(estudiante.nombres,TextView.BufferType.EDITABLE)
        apellidoEstudiante.setText(estudiante.apellidos, TextView.BufferType.EDITABLE )
        fechaNacEstudiante.setText(estudiante.fechaNacimiento, TextView.BufferType.EDITABLE)
        //  semestreActual.setText(actor.semestreActual.toString(), TextView.BufferType.EDITABLE)




    }

    override fun onClick(view: View?) {
        when (view?.id){
            R.id.editText_fechaNac ->{
                obtenerFecha()
            }

        }
    }

    private fun obtenerFecha() {
        val recogerFecha = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
            val mesActual = month + 1

            //Formateo el día obtenido: antepone el 0 si son menores de 10
            val diaFormateado =
                    if (dayOfMonth < 10) CERO + dayOfMonth.toString()
                    else dayOfMonth.toString()

            //Formateo el mes obtenido: antepone el 0 si son menores de 10
            val mesFormateado =
                    if (mesActual < 10) CERO + mesActual.toString()
                    else mesActual.toString()

            //Muestro la fecha con el formato deseado
            fechaNacEstudiante.setText(diaFormateado + BARRA + mesFormateado + BARRA + year)

        },
                //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual

                anio, mes, dia)
        //Muestro el widget
        recogerFecha.show()
    }
}
