package com.example.wilson.examen_moviles.entidades

import android.os.Parcel
import android.os.Parcelable


class Estudiante (val idEstudiante: Int, val nombres:String, val apellidos: String, val fechaNacimiento: String, val semestreActual: Int, val graduado : String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idEstudiante)
        parcel.writeString(nombres)
        parcel.writeString(apellidos)
        parcel.writeString(fechaNacimiento)
        parcel.writeInt(semestreActual)
        parcel.writeString(graduado)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }
}

