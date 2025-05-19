package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Categoria
import com.example.myapplication.model.Producto
import com.example.myapplication.ui.categoria.CategoriaAdapter
import com.example.myapplication.ui.categoria.CategoriaViewModel
import com.example.myapplication.ui.producto.ProductoAdapter

class MainActivity : AppCompatActivity(), ProductoAdapter.OnItemClickListener {

    private lateinit var recyclerViewProductos: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter
    private var listaDeProductos: List<Producto> = emptyList()
    private val carritoDeCompras = mutableListOf<Producto>()
    private lateinit var startDetalleActivityForResult: ActivityResultLauncher<Intent>
    private var carritoMenuItem: MenuItem? = null

    private val categoriaViewModel: CategoriaViewModel by viewModels()
    private lateinit var categoriaAdapter: CategoriaAdapter
    private lateinit var recyclerViewCategorias: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerViewCategorias = findViewById(R.id.recyclerViewCategorias)
        recyclerViewCategorias.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoriaAdapter = CategoriaAdapter(emptyList()) { categoria ->
            // OnClickListener para las categorías
            categoriaViewModel.obtenerProductosPorCategoria(categoria.categoria_id)
            val categoriaId = categoria.categoria_id

            Log.d("MainActivity", "Categoría clickeada en el Adapter: ${categoria.nombre}, ID para productos: $categoriaId")
            categoriaViewModel.obtenerProductosPorCategoria(categoriaId.toInt()) // Asegúrate de convertir a Int si tu endpoint lo espera

        }
        recyclerViewCategorias.adapter = categoriaAdapter

        recyclerViewProductos = findViewById(R.id.recyclerViewProductos)
        recyclerViewProductos.layoutManager = LinearLayoutManager(this)
        productoAdapter = ProductoAdapter(emptyList(), this)
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

        observarCategorias()
        observarProductosPorCategoria()
        observarErrores()
        observarLoading()

        actualizarContadorCarrito()
    }

    private fun observarCategorias() {
        categoriaViewModel.categorias.observe(this) { categorias ->
            categoriaAdapter.actualizarCategorias(categorias)
        }
    }

    private fun observarProductosPorCategoria() {
        categoriaViewModel.productosDeCategoria.observe(this) { productos ->
            Log.d("MainActivity", "Lista de productos observada, tamaño: ${productos.size}")
            productos.forEach {
                Log.d("MainActivity", "Producto: ${it.nombre}, Imagen URL: ${it.imagen}") // <--- Añade este log
            }
            listaDeProductos = productos
            productoAdapter.actualizarProductos(productos)
        }
    }

    private fun observarErrores() {
        categoriaViewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            Log.e("MainActivity", "Error: $error")
        }
    }

    private fun observarLoading() {
        categoriaViewModel.isLoadingCategorias.observe(this) { isLoading ->
            Log.d("MainActivity", "Cargando categorías: $isLoading")
            // Puedes mostrar/ocultar un ProgressBar para categorías aquí
        }

        categoriaViewModel.isLoadingProductos.observe(this) { isLoading ->
            Log.d("MainActivity", "Cargando productos: $isLoading")
            // Puedes mostrar/ocultar un ProgressBar para productos aquí
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        carritoMenuItem = menu?.findItem(R.id.action_carrito)
        actualizarContadorCarrito()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_carrito -> {
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
}