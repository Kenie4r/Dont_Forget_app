package com.cod3hn.dontforget.`class`

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cod3hn.dontforget.R
import com.cod3hn.dontforget.dashBoard
import com.cod3hn.dontforget.db.DbHelper
import kotlinx.android.synthetic.main.cardview.view.*

class adapter_card_view: RecyclerView.Adapter<adapter_card_view.ViewHolder>() {

    var db = DbHelper(dashBoard() )
    var data = db.getdata()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapter_card_view.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: adapter_card_view.ViewHolder, position: Int) {
        holder.itemTitulo.text = data[position].Titulo
        holder.itemdescrip.text = data[position].Descripcion
        holder.itemHoraIncio.text = data[position].HoraInicio
        holder.itemHoraFinal.text  =data[position].HoraFin
        holder.itemFechas.text  = data[position].FechaInicio + "- "+ data[position].FechaFinal

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder( itemView: View):RecyclerView.ViewHolder(itemView){
        var itemTitulo: TextView
        var itemdescrip: TextView
        var itemHoraIncio: TextView
        var itemHoraFinal: TextView
        var itemFechas: TextView


        init {
            itemTitulo = itemView.findViewById(R.id.tvTitulo)
            itemdescrip  =itemView.findViewById(R.id.tvDescripcion)
            itemHoraIncio = itemView.findViewById(R.id.tvHoraInicio)
            itemHoraFinal = itemView.findViewById(R.id.tvHorafinal)
            itemFechas = itemView.findViewById(R.id.tvFEchas)
        }
    }
}