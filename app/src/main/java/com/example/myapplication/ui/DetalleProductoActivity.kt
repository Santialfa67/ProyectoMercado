package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalleProductoActivity : AppCompatActivity() {

    private lateinit var textViewNombreDetalle: TextView
    private lateinit var textViewPrecioDetalle: TextView
    private lateinit var textViewDescripcionDetalle: TextView
    private lateinit var botonAgregarCarrito: Button
    private var producto: Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textViewNombreDetalle = findViewById(R.id.textViewNombreDetalle)
        textViewPrecioDetalle = findViewById(R.id.textViewPrecioDetalle)
        textViewDescripcionDetalle = findViewById(R.id.textViewDescripcionDetalle)
        botonAgregarCarrito = findViewById(R.id.botonAgregarCarrito)

        producto = intent.getParcelableExtra<Producto>("producto")

        producto?.let { producto ->
            textViewNombreDetalle.text = producto.nombre
            textViewPrecioDetalle.text = "$${String.format("%.2f", producto.precio)}"
            textViewDescripcionDetalle.text = producto.descripcion

            botonAgregarCarrito.setOnClickListener {
                val resultIntent = Intent()
                resultIntent.putExtra("producto_añadido", producto) // <- ahora sí es el Producto
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