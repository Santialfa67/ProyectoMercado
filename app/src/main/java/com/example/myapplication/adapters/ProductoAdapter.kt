package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private val listaDeProductos: List<Producto>, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(producto: Producto)
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombreProducto)
        val precioTextView: TextView = itemView.findViewById(R.id.textViewPrecioProducto)
        val descripcionTextView: TextView = itemView.findViewById(R.id.textViewDescripcionProducto)

        fun bind(producto: Producto, clickListener: OnItemClickListener) {
            nombreTextView.text = producto.nombre
            precioTextView.text = "$${String.format("%.2f", producto.precio)}"
            descripcionTextView.text = producto.descripcion

            itemView.setOnClickListener {
                clickListener.onItemClick(producto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val productoActual = listaDeProductos[position]
        holder.bind(productoActual, itemClickListener)
    }

    override fun getItemCount() = listaDeProductos.size
}