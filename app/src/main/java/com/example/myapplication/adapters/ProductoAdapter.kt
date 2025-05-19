package com.example.myapplication.ui.producto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Producto
import com.squareup.picasso.Picasso
class ProductoAdapter(
    private var listaDeProductos: List<Producto>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(producto: Producto)
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombreProducto)
        val precioTextView: TextView = itemView.findViewById(R.id.textViewPrecioProducto)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imageViewProducto)


        fun bind(producto: Producto) {
            nombreTextView.text = producto.nombre
            precioTextView.text = "$${producto.precio}"
            producto.imagen?.let { Picasso.get().load(it).into(imagenImageView) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val productoActual = listaDeProductos[position]
        holder.bind(productoActual)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(productoActual)
        }
    }

    override fun getItemCount() = listaDeProductos.size

    fun actualizarProductos(nuevaLista: List<Producto>) {
        listaDeProductos = nuevaLista
        notifyDataSetChanged()
    }
}

