package com.cod3hn.dontforget

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cod3hn.dontforget.`class`.Adapter_card_view
import com.cod3hn.dontforget.`class`.all_task
import com.cod3hn.dontforget.databinding.ActivityTareasBinding
import com.cod3hn.dontforget.db.DbHelper
import kotlinx.android.synthetic.main.activity_tareas.*
class tareas : AppCompatActivity() {

    private  lateinit var binding : ActivityTareasBinding
    private lateinit var DbHp : DbHelper
    private  lateinit var db : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_tareas)
        binding =ActivityTareasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DbHp= DbHelper(this)

        db = DbHp.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM tarea ", null)

        val adaptador = Adapter_card_view()


        adaptador.Adapter_card_view(this, cursor)

        binding.rviewTAsk.layoutManager = LinearLayoutManager(this )

        binding.rviewTAsk.adapter = adaptador





        binding.btnDashBoard.setOnClickListener(){
            var intent = Intent(this , dashBoard()::class.java)
            startActivity(intent)
            this.finish()
        }
        binding.menu.setOnClickListener(){
            var intent = Intent(this, crearTareas()::class.java)
            startActivity(intent)
            this.finish()
        }




     /*  var data = db.getdata(all_task())
        textView2.text = ""
    /*
       for ( i in 0..(data.size-1)){
           textView2.append(data[i]._ID.toString()+" "+data[i].Titulo.toString()+" "+data[i].Descripcion.toString())
       }*/
        btnDashBoard.setOnClickListener{
            val intent = Intent(this, dashBoard()::class.java)
            startActivity(intent)
        }*/

    }


    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}