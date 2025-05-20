package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView // Importa ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.model.Producto
import com.squareup.picasso.Picasso // Importa Picasso

class DetalleProductoActivity : AppCompatActivity() {

    private lateinit var textViewNombreDetalle: TextView
    private lateinit var textViewPrecioDetalle: TextView
    private lateinit var textViewDescripcionDetalle: TextView
    private lateinit var imageViewDetalle: ImageView // Declara ImageView
    private lateinit var textViewStockDetalle: TextView // Declara TextView para stock
    private lateinit var textViewProveedorDetalle: TextView // Declara TextView para proveedor
    private lateinit var botonAgregarCarrito: Button
    private var producto: Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Inicializa todos los TextViews y el ImageView
        textViewNombreDetalle = findViewById(R.id.textViewNombreDetalle)
        textViewPrecioDetalle = findViewById(R.id.textViewPrecioDetalle)
        textViewDescripcionDetalle = findViewById(R.id.textViewDescripcionDetalle)
        imageViewDetalle = findViewById(R.id.imageViewDetalle) // Inicializa ImageView
        textViewStockDetalle = findViewById(R.id.textViewStockDetalle) // Inicializa TextView stock
        textViewProveedorDetalle = findViewById(R.id.textViewProveedorDetalle) // Inicializa TextView proveedor
        botonAgregarCarrito = findViewById(R.id.botonAgregarCarrito)

        // Obtener el objeto Producto del Intent
        // Asegúrate de que el key "producto" es el mismo que usas al pasar el extra desde MainActivity
        producto = intent.getParcelableExtra<Producto>("producto")

        producto?.let { producto ->
            textViewNombreDetalle.text = producto.nombre
            textViewPrecioDetalle.text = "$${String.format("%.2f", producto.precio)}"
            textViewDescripcionDetalle.text = producto.descripcion

            // Cargar la imagen con Picasso
            producto.imagen?.let { imageUrl ->
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground) // O tu propio placeholder
                    .error(R.drawable.ic_launcher_background) // O tu propio imagen de error
                    .into(imageViewDetalle)
            } ?: run {
                // Si la URL de la imagen es nula o vacía, puedes mostrar una imagen por defecto
                imageViewDetalle.setImageResource(R.drawable.ic_launcher_background)
            }

            // Mostrar stock
            textViewStockDetalle.text = "Stock: ${producto.stock ?: "No disponible"}"

            // Mostrar proveedor
            // Si el proveedor es un objeto Proveedor con un campo 'nombre':
            textViewProveedorDetalle.text = "Proveedor: ${producto.proveedor?.nombre ?: "No disponible"}"
            // Si el proveedor es solo un String (ej. nombre del proveedor):
            // textViewProveedorDetalle.text = "Proveedor: ${producto.proveedor ?: "No disponible"}"


            botonAgregarCarrito.setOnClickListener {
                val resultIntent = Intent()
                resultIntent.putExtra("producto_añadido", producto)
                setResult(RESULT_OK, resultIntent)
                Toast.makeText(this, "${producto.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
                finish()
            }
        } ?: run {
            Toast.makeText(this, "Error: No se recibieron los detalles del producto", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}