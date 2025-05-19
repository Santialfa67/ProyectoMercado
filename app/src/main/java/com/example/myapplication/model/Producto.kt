package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Producto(
    val producto_id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String? // O el tipo de dato que uses para las imágenes
    // ... otros campos del producto
) : Parcelable