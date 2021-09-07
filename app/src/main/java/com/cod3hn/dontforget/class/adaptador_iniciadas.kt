package com.cod3hn.dontforget.`class`

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.PeriodicSync
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cod3hn.dontforget.R
import com.cod3hn.dontforget.dashBoard
import com.cod3hn.dontforget.databinding.CardviewIniciadasBinding
import com.cod3hn.dontforget.db.DbHelper

class adaptador_iniciadas: RecyclerView.Adapter<adaptador_iniciadas.ViewHolder>() {
    lateinit var context: Context
    lateinit var cursor : Cursor
    lateinit var binding: CardviewIniciadasBinding
    fun adaptador_iniciadas( context: Context, cursor: Cursor){
        this.context = context
        this.cursor  = cursor
    }

    inner class ViewHolder:RecyclerView.ViewHolder{
        var tvID : TextView
        var tvNombre: TextView
        var tvDescripcion: TextView
        var tvHoraInicio: TextView
        var tvHoraFInal: TextView
        var btnTerminar: Button
        constructor(view: View):super(view){
            val binding = CardviewIniciadasBinding.bind(view)
            tvID = binding.tvID
            tvNombre = binding.tvTitulo
            tvDescripcion = binding.tvDescripcion
            tvHoraFInal = binding.tvHorafinal
            tvHoraInicio = binding.tvHoraInicio
            btnTerminar = binding.btnFinalizar

            btnTerminar.setOnClickListener(){
                var dialog = AlertDialog.Builder(context)
                dialog.apply {
                    setTitle("¿Ya haz terminado?")
                    setPositiveButton("Sí, ya termine"){
                        dialog, wich->
                        var position = adapterPosition
                        cursor.moveToPosition(position)
                        var _ID = cursor.getString(0).toInt()
                        DbHelper(context).finished(_ID)
                        dashBoard.removeAlarm(context)
                        PreUtil.setTimerState(dashBoard.TimerState.Stopped, context)
                        notificacion.hideTimerNotification(context)
                        dashBoard().finish()
                        val intent = Intent(context, dashBoard::class.java)
                        tiempo(context).saveMinutos(0)
                        context.startActivity(intent)

                    }
                    setNegativeButton("No, aún me falta", null)
                }
                dialog.show()


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_iniciadas, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var activas = DbHelper(context).obtenerActivas()
        if(activas>0){
            cursor.moveToPosition(position)
            holder.tvID.text = cursor.getString(0)
            holder.tvNombre.text = cursor.getString(1)
            holder.tvDescripcion.text = cursor.getString(2)
            holder.tvHoraInicio.text = cursor.getString(3)
            holder.tvHoraFInal.text  = cursor.getString(4)

        }else{
            holder.tvNombre.text = ""
            holder.tvDescripcion.text = "Aún no hay tareas creadas, por favor crea una para comenzar"
            holder.tvHoraInicio.text = ""
            holder.tvHoraFInal.text  = ""
            holder.btnTerminar.isVisible  =false

        }


    }

    override fun getItemCount(): Int {
        if(cursor == null){
            return 0
        }else{
            return cursor.count
        }
    }


}