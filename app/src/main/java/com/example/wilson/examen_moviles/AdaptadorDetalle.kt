package com.example.wilson.examen_moviles

import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerEstudiante
import com.example.wilson.examen_moviles.baseDeDatos.DbHandlerMateria
import com.example.wilson.examen_moviles.entidades.Materia

class AdaptadorDetalle(internal var arrayMateria: ArrayList<Materia>, internal var ctx: Context): RecyclerView.Adapter<AdaptadorDetalle.ViewHolderDetalle>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDetalle {
        //Reutilizamos el mismo item_list
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_estudiante,null,false)

        return ViewHolderDetalle(view)

    }


    override fun onBindViewHolder(holder: ViewHolderDetalle, position: Int) {

        holder.nombreMateria.text = arrayMateria[position].idMateria.toString()
        holder.codigo.text = arrayMateria[position].codigo
        holder.activo.text=arrayMateria[position].activo

        holder.botonDetalleMateria.setOnClickListener {
            view: View? -> irAActividadDetalleMateria(position)
        }
    }

    private fun irAActividadDetalleMateria(posicion: Int) {
        val intent = Intent(ctx, ActivityDetalleMateria::class.java)
        intent.putExtra("materia-seleccionada",arrayMateria[posicion])
        ctx.startActivity(intent)
    }


    override fun getItemCount(): Int {

        return arrayMateria.size
    }





    inner class ViewHolderDetalle (itemView: View): RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {


        internal var nombreMateria: TextView
        internal var codigo: TextView
        internal var activo: TextView
        internal var botonDetalleMateria: Button

        init {

            nombreMateria = itemView.findViewById(R.id.txtView_nombreApellido)
            codigo = itemView.findViewById(R.id.txtView_semestreActual)
            activo = itemView.findViewById(R.id.txtView_graduado)
            botonDetalleMateria=itemView.findViewById(R.id.btn_detalles)

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
//            editar?.setOnMenuItemClickListener { menuItem: MenuItem? ->
//                //editarMateria()
//            }

            eliminar?.setOnMenuItemClickListener { menuItem: MenuItem? ->
                elimnarMateria()
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

        fun elimnarMateria(): Boolean {
            val dbHandlerMateria = DbHandlerMateria(ctx)
            val modal = AlertDialog.Builder(ctx)
            modal.setMessage(R.string.nombreOpcionConfirmar)
                    .setPositiveButton(R.string.Si, { dialog, which ->
                        dbHandlerMateria.eliminarMateria(arrayMateria[adapterPosition].idMateria)
                    }
                    )
                    .setNegativeButton(R.string.No, null)
            val dialogo = modal.create()
            dialogo.show()
            return true
        }






//        fun editarMateria(): Boolean {
//
//            val intent = Intent(ctx, ActivityEditarEstudiante::class.java)
//            intent.putExtra("estudiante-a-editar", arrayEstudiantes[adapterPosition])
//            ctx.startActivity(intent)
//
//            return true
//
//        }


    }



}