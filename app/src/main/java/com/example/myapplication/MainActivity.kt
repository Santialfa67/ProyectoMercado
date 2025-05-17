package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ProductoAdapter.OnItemClickListener {

    private lateinit var recyclerViewProductos: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var listaDeProductos: List<Producto>
    private val carritoDeCompras = mutableListOf<Producto>()
    private lateinit var startDetalleActivityForResult: ActivityResultLauncher<Intent>
    private var carritoMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerViewProductos = findViewById(R.id.recyclerViewProductos)

        listaDeProductos = listOf(
            Producto("Laptop HP", 799.99, "Potente laptop para trabajar y jugar."),
            Producto("Mouse Logitech", 25.50, "Mouse inalámbrico cómodo y preciso."),
            Producto("Teclado Mecánico", 120.00, "Teclado con switches mecánicos para una mejor experiencia de escritura."),
            Producto("Monitor 24 pulgadas", 250.75, "Monitor Full HD con colores vibrantes."),
            Producto("Laptop Dell", 999.99, "Laptop de alta gama para profesionales."),
            Producto("Webcam HD", 45.00, "Webcam de alta definición para videollamadas.")
        )

        productoAdapter = ProductoAdapter(listaDeProductos, this)

        recyclerViewProductos.layoutManager = LinearLayoutManager(this)
        recyclerViewProductos.adapter = productoAdapter

        startDetalleActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val productoAñadido = result.data?.getParcelableExtra<Producto>("producto_añadido")
                productoAñadido?.let {
                    carritoDeCompras.add(it)
                    actualizarContadorCarrito()
                    Toast.makeText(this, "${it.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
                }
            }
        }
        actualizarContadorCarrito()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        carritoMenuItem = menu?.findItem(R.id.action_carrito)
        actualizarContadorCarrito()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", "Item seleccionado: ${item.itemId}") // Añade este log
        return when (item.itemId) {
            R.id.action_carrito -> {
                Log.d("MainActivity", "Carrito clickeado") // Añade este log
                // Iniciar la CarritoActivity y pasar la lista del carrito
                val intent = Intent(this, CarritoActivity::class.java)
                intent.putParcelableArrayListExtra("carrito", ArrayList(carritoDeCompras))
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(producto: Producto) {
        val intent = Intent(this, DetalleProductoActivity::class.java)
        intent.putExtra("producto", producto)
        startDetalleActivityForResult.launch(intent)
    }

    private fun actualizarContadorCarrito() {
        carritoMenuItem?.actionView?.findViewById<TextView>(R.id.cart_badge)?.text = carritoDeCompras.size.toString()
        invalidateOptionsMenu() // Fuerza la recreación del menú
    }


    //FALTA LOGICA DE ELIMINAR EN EL CARRITO
    //AGREGAR CATEGORIAS
}