package com.example.wilson.examen_moviles

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.view.menu.ShowableListMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerEstudiante
import com.example.wilson.examen_moviles.entidades.Estudiante

class AdaptadorEstudiante(internal var arrayEstudiantes: ArrayList<Estudiante>, internal var ctx: Context): RecyclerView.Adapter<AdaptadorEstudiante.ViewHolderEstudiante>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEstudiante {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_estudiante, null, false)

        return ViewHolderEstudiante(view)

    }


    override fun onBindViewHolder(holder: ViewHolderEstudiante, position: Int) {


        holder.nombreApellido.text = arrayEstudiantes[position].nombres + " " + arrayEstudiantes[position].apellidos
        holder.semestreActual.text = arrayEstudiantes[position].semestreActual.toString() + " " + ctx.getString(R.string.Materias)

        if (arrayEstudiantes[position].graduado == "Graduado") {
            holder.graduado.text = ctx.getString(R.string.elEstudianteEstaGraduado)
        } else {
            holder.graduado.text = ctx.getString(R.string.elEstudianteNoestaGraduado)

        }



        holder.botonDetalles.setOnClickListener { view: View ->
            irAActividadDetalles(position)
        }


    }


    private fun irAActividadDetalles(position: Int) {
        val intent = Intent(ctx, DetalleActivity::class.java)
        intent.putExtra("estudiante-seleccionado", arrayEstudiantes[position])
        ctx.startActivity(intent)

    }


    override fun getItemCount(): Int {

        return arrayEstudiantes.size
    }


    inner class ViewHolderEstudiante(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {


        internal var nombreApellido: TextView
        internal var semestreActual: TextView
        internal var graduado: TextView

        internal var botonDetalles: Button

        init {

            nombreApellido = itemView.findViewById(R.id.txtView_nombreApellido)
            semestreActual = itemView.findViewById(R.id.txtView_semestreActual)
            graduado = itemView.findViewById(R.id.txtView_graduado)

            botonDetalles = itemView.findViewById(R.id.btn_detalles)

            itemView.setOnCreateContextMenuListener(this)


        }

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, contextMenuInfo: ContextMenu.ContextMenuInfo?) {

            var editar: MenuItem?
            var eliminar: MenuItem?
            var enviarCorreo: MenuItem?
//            var confirmar: MenuItem?
//            var cancelar: MenuItem?

            editar = menu?.add(ctx.getString(R.string.nombreOpcionEditar))
            eliminar = menu?.add(ctx.getString(R.string.nombreOpcionELiminar))
            enviarCorreo = menu?.add(ctx.getString(R.string.nombreOpcionCorreo))
//            confirmar = menu?.add(ctx.getString(R.string.nombreOpcionConfirmar))
//            cancelar = menu?.add(ctx.getString(R.string.nombreOpcionCancelar))
            editar?.setOnMenuItemClickListener { menuItem: MenuItem? ->
                editarEstudiante()
            }

            eliminar?.setOnMenuItemClickListener { menuItem: MenuItem? ->
                elimnarEstudiante()
            }

            enviarCorreo?.setOnMenuItemClickListener { menuItem: MenuItem? ->
                enviarCorreo()
            }

        }
//        fun salir(): Boolean{
//            return true
//        }

        fun enviarCorreo(): Boolean {

            val addresses = arrayListOf("", "")
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_EMAIL, addresses)
            intent.putExtra(Intent.EXTRA_SUBJECT, "")
            intent.putExtra(Intent.EXTRA_TEXT, "")
            ctx.startActivity(intent)

            return true

        }

        fun elimnarEstudiante(): Boolean {
            val dbHandlerEstudiante = DbHandlerEstudiante(ctx)
            val modal = AlertDialog.Builder(ctx)
            modal.setMessage(R.string.nombreOpcionConfirmar)
                    .setPositiveButton(R.string.Si, { dialog, which ->
                        dbHandlerEstudiante.eliminarEstudiante(arrayEstudiantes[adapterPosition].idEstudiante)
                    }
                    )
                    .setNegativeButton(R.string.No, null)
            val dialogo = modal.create()
            dialogo.show()
            return true
        }


        fun editarEstudiante(): Boolean {

            val intent = Intent(ctx, ActivityEditarEstudiante::class.java)
            intent.putExtra("estudiante-a-editar", arrayEstudiantes[adapterPosition])
            ctx.startActivity(intent)

            return true

        }


    }

}



