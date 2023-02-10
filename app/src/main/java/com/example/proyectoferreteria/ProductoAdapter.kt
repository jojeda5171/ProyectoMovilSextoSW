package com.example.proyectoferreteria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoferreteria.databinding.ItemProductosBinding
//import com.example.proyectoferreteria.databinding.ItemProductosBinding
import com.example.proyectoferreteria.model.Product
import com.squareup.picasso.Picasso

class ProductoAdapter (var productos:List<Product> = emptyList()): RecyclerView.Adapter<ProductoAdapter.ProductoAdapterViewHolder>() {
    //definir las funciones para manipular los registro
    lateinit var setOnClickListenerProductEdit: (Product)->Unit
    lateinit var setOnClickListenerProductDelete:(Product)->Unit
    lateinit var setOnClickDetalle:(Product)->Unit
    //Crear el ViewHolder
    inner class ProductoAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var binding : ItemProductosBinding = ItemProductosBinding.bind(itemView)
        fun bind(producto: Product){
            binding.txtNombreProducto.text =producto.nombre
            binding.txtDetalleProducto.text = producto.descripcion
            binding.txtPrecio.text = producto.costo
            binding.txtCantidad.text=producto.cantidad
            Picasso.get().load(producto.foto).error(R.drawable.ic_launcher_background).into(binding.imageView3)

            binding.btnEditar.setOnClickListener {
                setOnClickListenerProductEdit(producto)
            }

            binding.btnEliminar.setOnClickListener {
                setOnClickListenerProductDelete(producto)
            }

            binding.itemProducto.setOnClickListener {
                setOnClickDetalle(producto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoAdapterViewHolder {
        //Para implemetar una vista
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_productos,parent,false)
        return ProductoAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoAdapterViewHolder, position: Int) {
        //posicion de cada elemenot en la vista
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    //Editar

    fun updateListProducts(productos: List<Product>){
        this.productos=productos
        notifyDataSetChanged()
    }
}