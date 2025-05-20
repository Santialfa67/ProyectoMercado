package com.example.myapplication.network

import com.example.myapplication.model.Producto
import com.example.myapplication.model.Categoria
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

//interface ApiService {
//    @GET("/api/categorias") // El endpoint específico para las categorías (relativo a la BASE_URL)
//    suspend fun obtenerCategorias(
//        @Header("apikey") apiKey: String,
//        @Header("Content-Type") contentType: String = "application/json",
//        @Header("Accept") accept: String = "application/vnd.pgrst.object+json" // O application/json si esperas un array
//    ): List<Categoria>
//}
//interface ApiService {
//    @GET("/rest/v1/categorias")
//    suspend fun obtenerCategorias(
//        @Header("apikey") apiKey: String,
//        @Header("Authorization") authorization: String,
//        @Header("Accept") accept: String = "application/json"
//    ): List<Categoria>
//}

//interface ApiService {
//    @GET("api/categorias") // Endpoint correcto
//    suspend fun obtenerCategorias(): List<Categoria>
//}

interface ApiService {
    @GET("api/categorias") // Endpoint para obtener las categorías (ajusta si es diferente en tu API local)
    suspend fun obtenerCategorias(
        //@Header("Content-Type") contentType: String = "application/json",
        //@Header("Accept") accept: String = "application/vnd.pgrst.object+json" // Ajusta según el tipo de respuesta de tu API local
    ): List<Categoria>

    @GET("api/productos/categoria/{categoriaId}") // Nuevo endpoint
    suspend fun obtenerProductosPorCategoria(
        @Path("categoriaId") categoriaId: Int
    ): List<Producto>


    @GET("api/productos/{id}")
    suspend fun obtenerDetalleProducto(
        @Path("id") id: Int
    ): Producto
}