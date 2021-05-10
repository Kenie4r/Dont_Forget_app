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
import com.cod3hn.dontforget.dashBoard
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
                    var minutos = 0
                    if(cursor.getString( 5 )== cursor.getString(6)){
                        var fechaI = cursor.getString(5)
                        var fechaF = cursor.getString( 6)
                        var arrayFechaI = fechaI.split("/").toTypedArray()
                        var arrayFechaF = fechaF.split("/").toTypedArray()
                        var HoraII = cursor.getString(3)
                        var HoraF  = cursor.getString( 4)
                        var arrayHoraF = HoraF.split(":").toTypedArray()
                        var arrayHoraII = HoraII.split(":").toTypedArray()
                        var minutosI = arrayHoraII[1].toInt()
                        var minutosF = arrayHoraF[1].toInt()
                        var horitaF = arrayHoraF[0].toInt()
                        var horitaI = arrayHoraII[0].toInt()
                        var anio1 = arrayFechaI[2].toInt()
                        var mes1 = arrayFechaI[1].toInt()
                        var dia1 = arrayFechaI[0].toInt()
                        var anio2 = arrayFechaF[2].toInt()
                        var mes2 = arrayFechaF[1].toInt()
                        var dia2 = arrayFechaF[0].toInt()

                        if(dia1==dia2){
                            if(horitaF==horitaI){
                                minutos = minutosF-minutosI
                            }else if(horitaF>horitaI){
                                var horas = horitaF-horitaI
                                horas *=60
                                minutos = (minutosF-minutosI) + horas
                            }
                        }else{
                            var nDias = dia2-dia1
                            var horas = horitaF-horitaI


                            nDias *=24
                            nDias-=horas
                            nDias *=60
                            minutos = (minutosF-minutosI) + nDias
                        }

                    }

                    tiempo(context).saveMinutos(minutos)
                    tiempo(context).saveID(_ID)

                    var actividad = Intent(context, dashBoard::class.java)
                    context.startActivity(actividad)

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


