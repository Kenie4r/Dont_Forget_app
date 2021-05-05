package com.cod3hn.dontforget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cod3hn.dontforget.`class`.adapter_card_view
import kotlinx.android.synthetic.main.activity_tareas.*
class tareas : AppCompatActivity() {


    private  var layoutManager: RecyclerView.LayoutManager? = null
    private var adaptador: RecyclerView.Adapter<adapter_card_view.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareas)

        layoutManager  = LinearLayoutManager(this)
        rviewTAsk.layoutManager = layoutManager

        adaptador  = adapter_card_view()

        rviewTAsk.adapter  = adaptador


    }
}