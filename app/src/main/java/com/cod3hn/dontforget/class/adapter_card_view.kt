package com.cod3hn.dontforget.`class`

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.cod3hn.dontforget.R
import com.cod3hn.dontforget.databinding.CardviewBinding
import com.cod3hn.dontforget.db.DbHelper
import com.cod3hn.dontforget.edit_task

class Adapter_card_view: RecyclerView.Adapter<Adapter_card_view.ViewHolder>() {


    lateinit var context: Context
    lateinit var cursor : Cursor


    fun Adapter_card_view( context: Context, cursor: Cursor){
        this.context = context
        this.cursor  = cursor
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_card_view.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Adapter_card_view.ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.itemID.text  = cursor.getString( 0)
        holder.itemTitulo.text = cursor.getString(1)
        holder.itemdescrip.text = cursor.getString(2)
        holder.itemHoraIncio.text = cursor.getString(3)
        holder.itemHoraFinal.text  = cursor.getString(4)
        holder.itemFechas.text  = cursor.getString(5) + "- "+ cursor.getString(6)
    }

    override fun getItemCount(): Int {
       if(cursor == null){
           return 0
       }else{
           return cursor.count
       }
    }

    inner class ViewHolder:RecyclerView.ViewHolder{
        var itemID:TextView
        var   itemTitulo : TextView
        var itemdescrip: TextView
        var itemHoraIncio: TextView
        var itemHoraFinal: TextView
        var itemFechas: TextView
        var btnEdit: Button
        var btnStart:Button
        constructor(view: View):super(view){
            val bindingItemsRV = CardviewBinding.bind(view)
            itemID = bindingItemsRV.tvID
            itemTitulo = bindingItemsRV.tvTitulo
            itemdescrip  = bindingItemsRV.tvDescripcion
            itemHoraIncio = bindingItemsRV.tvHoraInicio
            itemHoraFinal = bindingItemsRV.tvHorafinal
            itemFechas = bindingItemsRV.tvFEchas
            btnEdit = bindingItemsRV.btnEdit
            btnStart = bindingItemsRV.btnStart
            btnStart.setOnClickListener(){
                var nActivas = DbHelper(context).obtenerActivas()
                if(nActivas>0){
                    Toast.makeText(context, "Ya se encuentra un tarea activa, debes terminarla para comenzar otra", Toast.LENGTH_LONG).show()
                }else{
                    var position = adapterPosition
                    cursor.moveToPosition(position)
                    var _ID = cursor.getString(0).toInt()
                    DbHelper(context).inProgress(_ID)
                }

            }

            btnEdit.setOnClickListener(){
                var position = adapterPosition
                cursor.moveToPosition(position)
                var abrir  = Intent(context, edit_task()::class.java).apply {
                    putExtra("_ID", cursor.getString(0))
                }
                    context.startActivity(abrir)
            }
        }

    }
}