package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Producto
import com.example.myapplication.R

class CarritoAdapter(private val listaDeCarrito: List<Producto>) :
    RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombreCarrito)
        val precioTextView: TextView = itemView.findViewById(R.id.textViewPrecioCarrito)
        val eliminarButton: Button = itemView.findViewById(R.id.buttonEliminarCarrito)

        fun bind(producto: Producto) {
            nombreTextView.text = producto.nombre
            precioTextView.text = "$${String.format("%.2f", producto.precio)}"

            eliminarButton.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val productoActual = listaDeCarrito[position]
        holder.bind(productoActual)
    }

    override fun getItemCount() = listaDeCarrito.size
}