package com.example.wilson.examen_moviles

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.beust.klaxon.Klaxon
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerEstudiante
import com.example.wilson.examen_moviles.entidades.Estudiante
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_actividad_crear.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class ActividadCrear : AppCompatActivity(), View.OnClickListener{

    var c = Calendar.getInstance()

    var mes = c.get(Calendar.MONTH)
    var dia = c.get(Calendar.DAY_OF_MONTH)
    var anio = c.get(Calendar.YEAR)

    lateinit var txtFecha: EditText

    var CERO = "0"
    var BARRA = "/"

    lateinit var arregloOpciones : ArrayList<String>
    var graduado:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_crear)

        txtFecha = findViewById(R.id.editText_fechaNac)
        txtFecha.setOnClickListener(this)


        arregloOpciones = ArrayList()
        arregloOpciones.add(this.getString(R.string.opcionGraduadoSpinner))
        arregloOpciones.add(this.getString(R.string.opcionNoGraduadoSpinner))

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
                }else if(posicion==1){
                    graduado= arregloOpciones[1]
                }

            }

        }


        //BOTON REGISTRAR USUARIO
        btn_RegistrarEstudiante.setOnClickListener{
            view: View? -> registrarEstudiante()
        }
    }

    private fun registrarEstudiante() {
        val nombreEstudiante:EditText = findViewById(R.id.editTxt_nombreEstudiante)
        val apellidoEstudiante:EditText = findViewById(R.id.editTxt_apellidoEstudiante)
        val json = JSONObject()
        json.put("idEstudiante", 1)
        json.put("nombres", nombreEstudiante.text)

        json.put("apellidos", apellidoEstudiante.text)

        json.put("fechaNacimiento", "25062018")

        json.put("semestreActual", 7)

        json.put("graduado", "Graduado")

        val httpRequest = Fuel.post("http://192.168.100.189:1337/Estudiante").body(json.toString())
        httpRequest.headers["Content-Type"] = "application/json"
        httpRequest.response { request, response, result ->

            Log.i("mensaje",request.toString())

            Log.i("mensaje",response.toString())

            Log.i("mensaje",result.toString())
        }

        val intent = Intent (this, MainActivity::class.java)
        finish()
        startActivity(intent)




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
            txtFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year)

        },
                //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual

                anio, mes, dia)
        //Muestro el widget
        recogerFecha.show()

    }


}





