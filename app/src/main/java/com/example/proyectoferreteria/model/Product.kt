package com.example.proyectoferreteria.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tblProduct")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "tipo")
    var tipo: String,
    @ColumnInfo(name = "costo")
    var costo: String,
    @ColumnInfo(name = "descripcion")
    var descripcion: String,
    @ColumnInfo(name = "cantidad")
    var cantidad: String,
    @ColumnInfo(name = "foto")
    var foto: String
) : Serializable