package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.ui.RegisterActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.username)
        val passwordInput = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<ImageButton>(R.id.btnLogin)
        val registerButton = findViewById<TextView>(R.id.btnRegister)
        val intent = Intent(this, MainActivity::class.java)


        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            // Simulación de autenticación
            if (username == "admin" && password == "1234") {
                Toast.makeText(this, "Acceso correcto", Toast.LENGTH_SHORT).show()
                // Ir a MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // evita volver al login con el botón atrás
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            Toast.makeText(this, "Redirigir a pantalla de registro", Toast.LENGTH_SHORT).show()
            // Aquí podrías abrir RegisterActivity si tienes una
        }
    }
}