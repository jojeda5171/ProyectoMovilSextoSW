package com.example.proyectoferreteria.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proyectoferreteria.model.User

@Dao
interface UserDAO {
    //Definir el CRUD
    //Insert que devuelve el numero de registro insertado
    @Insert
    fun insert(user: User): Long

    //Actualizar sin devolver nada
    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    //Devuelve toda la lista de mascotas
    @Query("select * from tblUser order by id")
    fun getUsers(): LiveData<List<User>>

    @Query("select * from tblUser where id=:idInput")
    fun getUserById(idInput: Int): User

    @Query("select * from tblUser where usuario=:userInput and pass=:passInput")
    fun getUserByUserAndPass(userInput: String, passInput: String): User
}