package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarritoActivity : AppCompatActivity(), CarritoAdapter.OnItemClickListener {

    private lateinit var recyclerViewCarrito: RecyclerView
    private lateinit var carritoAdapter: CarritoAdapter
    private lateinit var textViewTotalCarrito: TextView
    private var listaDeCarrito: MutableList<Producto> = mutableListOf() // Cambiamos a MutableList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        // Inicializar vistas
        recyclerViewCarrito = findViewById(R.id.recyclerViewCarrito)
        textViewTotalCarrito = findViewById(R.id.textViewTotalCarrito)

        // ... (el resto de tu código onCreate) ...

        // Obtener la lista del carrito desde MainActivity
        listaDeCarrito = (intent.getParcelableArrayListExtra<Producto>("carrito") ?: mutableListOf())

        // Configurar el RecyclerView
        recyclerViewCarrito.layoutManager = LinearLayoutManager(this)
        carritoAdapter = CarritoAdapter(listaDeCarrito)
        recyclerViewCarrito.adapter = carritoAdapter

        // Registrar el listener para los clics del botón eliminar
        carritoAdapter.setOnItemClickListener(this)

        // Calcular y mostrar el total
        actualizarTotalCarrito()
    }

    override fun onItemEliminarClick(position: Int) {
        listaDeCarrito.removeAt(position)
        carritoAdapter.notifyItemRemoved(position)
        actualizarTotalCarrito()
        // Opcionalmente, podrías mostrar un Toast o Snackbar de confirmación
    }

    private fun actualizarTotalCarrito() {
        val total = listaDeCarrito.sumOf { it.precio }
        textViewTotalCarrito.text = "Total: $${String.format("%.2f", total)}"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}