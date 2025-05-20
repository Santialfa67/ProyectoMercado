package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Producto(
    val producto_id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String?,
    val stock: Int?,
    val proveedor: Proveedor? // Si la información del proveedor es un objeto
    // O val proveedor: String? si solo envías el nombre del proveedor como String
) : Parcelable

@Parcelize
data class Proveedor(
    val id: Int,
    val nombre: String
) : Parcelable