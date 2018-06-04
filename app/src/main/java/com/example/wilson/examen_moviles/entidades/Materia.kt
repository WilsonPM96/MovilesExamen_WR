package com.example.wilson.examen_moviles.entidades

import android.os.Parcel
import android.os.Parcelable

class Materia (val idMateria: Int,
               val codigo:String,
               val descripcion: String,
               val activo: String,
               val fechaCreacion: String,
               val numeroHorasPorSemana: Int,
               val estudianteId: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idMateria)
        parcel.writeString(codigo)
        parcel.writeString(descripcion)
        parcel.writeString(activo)
        parcel.writeString(fechaCreacion)
        parcel.writeInt(numeroHorasPorSemana)
        parcel.writeInt(estudianteId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Materia> {
        override fun createFromParcel(parcel: Parcel): Materia {
            return Materia(parcel)
        }

        override fun newArray(size: Int): Array<Materia?> {
            return arrayOfNulls(size)
        }
    }


}