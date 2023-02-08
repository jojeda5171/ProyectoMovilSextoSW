package com.example.proyectoferreteria.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proyectoferreteria.model.Product
import com.example.proyectoferreteria.model.User

@Dao
interface ProductDAO {
    //Definir el CRUD
    //Insert que devuelve el numero de registro insertado
    @Insert
    fun insert(product: Product): Long

    //Actualizar sin devolver nada
    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    //Devuelve toda la lista de mascotas
    @Query("select * from tblProduct order by id")
    fun getProduct(): LiveData<List<Product>>

    @Query("select * from tblProduct where id=:idInput")
    fun getProductById(idInput: Int): List<Product>
}