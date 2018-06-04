package com.example.wilson.examen_moviles

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView
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





    inner class ViewHolderDetalle (itemView: View): RecyclerView.ViewHolder(itemView) {


        internal var nombreMateria: TextView
        internal var codigo: TextView
        internal var activo: TextView
        internal var botonDetalleMateria: Button

        init {

            //Los IDs de los componentes son los que corresponden a item_list_actor que estamos reutilizando
            nombreMateria = itemView.findViewById(R.id.txtView_nombreApellido)
            codigo = itemView.findViewById(R.id.txtView_semestreActual)
            activo = itemView.findViewById(R.id.txtView_graduado)
            botonDetalleMateria=itemView.findViewById(R.id.btn_detalles)


        }





    }



}