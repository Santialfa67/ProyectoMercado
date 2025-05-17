package com.example.myapplication

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Producto(
    val nombre: String,
    val precio: Double,
    val descripcion: String
) : Parcelable