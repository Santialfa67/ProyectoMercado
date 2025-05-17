package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.CarritoAdapter

class CarritoActivity : AppCompatActivity() {

    private lateinit var recyclerViewCarrito: RecyclerView
    private lateinit var carritoAdapter: CarritoAdapter
    private lateinit var textViewTotalCarrito: TextView
    private var listaDeCarrito: List<Producto> = emptyList() // Inicialmente vacío

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val toolbarCarrito: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_carrito)
        setSupportActionBar(toolbarCarrito)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Botón "Atrás"

        recyclerViewCarrito = findViewById(R.id.recyclerViewCarrito)
        textViewTotalCarrito = findViewById(R.id.textViewTotalCarrito)

        // Obtener la lista del carrito desde MainActivity
        listaDeCarrito = (intent.getParcelableArrayListExtra<Producto>("carrito") ?: emptyList())

        // Configurar el RecyclerView
        recyclerViewCarrito.layoutManager = LinearLayoutManager(this)
        carritoAdapter = CarritoAdapter(listaDeCarrito) // Todavía no hemos creado el CarritoAdapter
        recyclerViewCarrito.adapter = carritoAdapter

        // Calcular y mostrar el total
        actualizarTotalCarrito()
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