package com.example.myapplication.ui.categoria

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Categoria
import com.example.myapplication.model.Producto
import com.example.myapplication.network.CategoriaService // Importa el nuevo servicio
import com.example.myapplication.network.ProductoService
import kotlinx.coroutines.launch

class CategoriaViewModel : ViewModel() {
    private val categoriaService = CategoriaService()
    private val productoService = ProductoService()

    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> = _categorias

    private val _productosDeCategoria = MutableLiveData<List<Producto>>()
    val productosDeCategoria: LiveData<List<Producto>> = _productosDeCategoria

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoadingCategorias = MutableLiveData<Boolean>()
    val isLoadingCategorias: LiveData<Boolean> = _isLoadingCategorias

    private val _isLoadingProductos = MutableLiveData<Boolean>()
    val isLoadingProductos: LiveData<Boolean> = _isLoadingProductos

    init {
        obtenerCategorias()
    }

    fun obtenerCategorias() {

        _isLoadingCategorias.value = true

        viewModelScope.launch {
            try {
                val categoriasResponse = categoriaService.obtenerCategorias()
                Log.d("CategoriaViewModel", "Categorías recibidas: ${categoriasResponse.joinToString { "${it.nombre} (${it.categoria_id})" }}")
                _categorias.value = categoriasResponse

                _isLoadingCategorias.value = false
            } catch (e: Exception) {
                _error.value = "Error al obtener las categorías: ${e.localizedMessage}"
                _isLoadingCategorias.value = false
            }
        }
    }

    fun obtenerProductosPorCategoria(categoriaId: Int) {
        _isLoadingProductos.value = true
        viewModelScope.launch {
            try {
                val productosResponse = productoService.obtenerProductosPorCategoria(categoriaId)
                _productosDeCategoria.value = productosResponse
                _isLoadingProductos.value = false
            } catch (e: Exception) {
                _error.value = "Error al obtener los productos: ${e.localizedMessage}"
                _isLoadingProductos.value = false
            }
        }
    }
}