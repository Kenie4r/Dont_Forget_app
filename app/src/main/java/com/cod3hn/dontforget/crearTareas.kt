package com.cod3hn.dontforget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.cod3hn.dontforget.`class`.tarea
import com.cod3hn.dontforget.db.DbHelper
import com.cod3hn.dontforget.fragments.DatePickerFragment
import com.cod3hn.dontforget.fragments.timepickerfragment
import kotlinx.android.synthetic.main.activity_crear_tareas.*

class crearTareas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tareas)
       btn_insert.setOnClickListener {
            if(etNombre.text.toString().length > 0 && etDescrip.text.toString().length>0 &&
                    tvFechaFin.text.toString().length>0 && tvHoraFin.text.toString().length>0&&
                    tvHoraIncio.text.toString().length>0 && tvFechaInicio.text.toString().length>0){

                var titulo:String = findViewById<EditText>(R.id.etNombre).text.toString()
                var descripcion:String = findViewById<EditText>(R.id.etDescrip).text.toString()
                var horaFin:String = findViewById<TextView>(R.id.tvHoraFin).text.toString()
                var horaInicio:String = findViewById<TextView>(R.id.tvHoraIncio).text.toString()
                var fechaInicio:String = findViewById<TextView>(R.id.tvFechaInicio).text.toString()
                var fechaFin:String = findViewById<TextView>(R.id.tvFechaFin).text.toString()
                var tarea = tarea(titulo, descripcion, horaInicio, horaFin, fechaInicio, fechaFin)
                var database = DbHelper(this)
                database.IngresarTarea(tarea)
            }else{
                Toast.makeText(this, "Porfavor rellene todos los campos", Toast.LENGTH_SHORT)
            }
       }

    }

    fun click(view: View){
        TimePickerOpen()
    }
    fun  TimePickerOpen (){
        val timepicker = timepickerfragment{ OnTimeSelected(it)}
        timepicker.show(supportFragmentManager , "time")
    }

    fun OnTimeSelected (time:String){
        val textView =  findViewById<TextView>(R.id.tvHoraIncio)
        textView.apply {
            text = "$time"
        }
    }
    fun click2 (view: View){
        TimePickerOpen2()
    }
    fun  TimePickerOpen2 (){
        val timepicker = timepickerfragment{ OnTimeSelected2(it)}
        timepicker.show(supportFragmentManager , "time")
    }

    fun OnTimeSelected2 (time:String){
        val textView =  findViewById<TextView>(R.id.tvHoraFin)
        textView.apply {
            text = "$time"
        }
    }



    fun DiaInicioClick (view: View){
        CalendarPickerOpen()
    }

    private fun CalendarPickerOpen() {
        val datepicker = DatePickerFragment {day, month, year -> onDateSelectedDay(day, month, year)}
        datepicker.show(supportFragmentManager, "datepicker")
    }

    private fun onDateSelectedDay(day: Int, month: Int, year: Int) {
        val textview = findViewById<TextView>(R.id.tvFechaInicio)

        textview.apply {
            text = "$day/$month/$year"
        }
    }

    fun DiaInicioClick2 (view: View){
        CalendarPickerOpen2()
    }

    private fun CalendarPickerOpen2() {
        val datepicker = DatePickerFragment {day, month, year -> onDateSelectedDay2(day, month, year)}
        datepicker.show(supportFragmentManager, "datepicker")
    }

    private fun onDateSelectedDay2(day: Int, month: Int, year: Int) {
        val textview = findViewById<TextView>(R.id.tvFechaFin)

        textview.apply {
            text = "$day/$month/$year"
        }
    }
    fun abrirDash( view: View){
        var abrir = Intent( this, dashBoard::class.java)
        startActivity(abrir)
        this.finish()
    }

}