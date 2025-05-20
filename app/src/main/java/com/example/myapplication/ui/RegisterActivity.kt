package com.example.myapp.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputEditText
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull


class RegisterActivity : AppCompatActivity() {

    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var addressInput: TextInputEditText
    private lateinit var registerButton: ImageButton

    private val client = OkHttpClient()
    private val backendUrl = "http://10.0.2.2:8080/api/usuarios/register" // Cambia según tu IP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameInput = findViewById(R.id.register_name)
        emailInput = findViewById(R.id.register_email)
        passwordInput = findViewById(R.id.register_password)
        phoneInput = findViewById(R.id.register_phone)
        addressInput = findViewById(R.id.register_address)
        registerButton = findViewById(R.id.btnCreateAccount)

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val address = addressInput.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            showToast("Por favor completa todos los campos.")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Correo electrónico inválido.")
            return
        }

        // JSON para enviar al backend
        val json = JSONObject().apply {
            put("nombre", name)
            put("email", email)
            put("contrasena", password)
            put("telefono", phone)
            put("direccion", address)
        }

        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            json.toString()
        )

        val request = Request.Builder()
            .url(backendUrl)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread { showToast("Error de red: ${e.message}") }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        showToast("Registro exitoso.")
                        finish() // cerrar pantalla y volver al login
                    } else {
                        showToast("Error al registrar: ${response.code}")
                    }
                }
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }
}
