package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(private var listaDeCarrito: MutableList<Producto>) :
    RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    interface OnItemClickListener {
        fun onItemEliminarClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class CarritoViewHolder(itemView: View, private val listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {

        private val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombreCarrito)
        private val precioTextView: TextView = itemView.findViewById(R.id.textViewPrecioCarrito)
        private val eliminarButton: Button = itemView.findViewById(R.id.buttonEliminarCarrito)

        fun bind(producto: Producto) {
            nombreTextView.text = producto.nombre
            precioTextView.text = "$${String.format("%.2f", producto.precio)}"

            eliminarButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemEliminarClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val productoActual = listaDeCarrito[position]
        holder.bind(productoActual)
    }

    override fun getItemCount() = listaDeCarrito.size
}
