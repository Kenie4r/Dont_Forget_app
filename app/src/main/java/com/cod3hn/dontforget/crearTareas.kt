package com.cod3hn.dontforget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.cod3hn.dontforget.`class`.saveAlarm
import com.cod3hn.dontforget.`class`.tarea
import com.cod3hn.dontforget.db.DbHelper
import com.cod3hn.dontforget.fragments.DatePickerFragment
import com.cod3hn.dontforget.fragments.timepickerfragment
import kotlinx.android.synthetic.main.activity_crear_tareas.*
import java.util.*

class crearTareas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tareas)
       btn_insert.setOnClickListener {
           var fechaTexto = "Ingresar la fecha de incio"
           var fechaFinTexto = "Ingresar fecha  de fin"
           var horaText ="Ingrese la hora de inicio"
           var hora2Text = "Ingrese la hora de fin"
            if(etNombre.text.toString().length > 0 && etDescrip.text.toString().length>0 &&
                (tvFechaFin.text.toString().length>0 && tvFechaFin.text.toString() != fechaFinTexto ) &&
                (tvHoraFin.text.toString().length>0 && tvHoraFin.text.toString()!= hora2Text)&&
                ( tvHoraIncio.text.toString().length>0 && tvHoraIncio.text.toString()!=horaText)&&
                (tvFechaInicio.text.toString().length>0 && tvFechaInicio.text.toString() != fechaTexto)){

                    var fechaIncio = findViewById<TextView>(R.id.tvFechaInicio).text.toString()
                    var arrayFechaInicio = fechaIncio.split("/").toTypedArray()
                    var fechaFinal = findViewById<TextView>(R.id.tvFechaFin).text.toString()
                    var arrayFechFinal = fechaFinal.split("/").toTypedArray()
                    var HoraInicio = findViewById<TextView>(R.id.tvHoraIncio).text.toString()
                    var arrayHoraIncio = HoraInicio.split(":").toTypedArray()
                    var HoraFinal = findViewById<TextView>(R.id.tvHoraFin).text.toString()
                    var arrayHoraFinal = HoraFinal.split(":").toTypedArray()

                    val saveData = saveAlarm(applicationContext)
                    if(arrayFechFinal[2].toInt()>arrayFechaInicio[2].toInt()){
                        Toast.makeText(this, "Error: el trabajo debe terminar en el a??o actual", Toast.LENGTH_LONG).show()

                    }else if(arrayFechFinal[2].toInt()==arrayFechaInicio[2].toInt()){

                        //comprobar meses
                        if(arrayFechFinal[1].toInt()>arrayFechaInicio[1].toInt()){
                            Toast.makeText(this, "Error: Las tareas deben ser cortas", Toast.LENGTH_LONG).show()

                        }else if(arrayFechFinal[1].toInt()==arrayFechaInicio[1].toInt()){
                            //validacion de d??as
                            if(arrayFechFinal[0].toInt()>arrayFechaInicio[0].toInt()){
                                Guardar()
                            }else if(arrayFechFinal[0].toInt()==arrayFechaInicio[0].toInt()){
                                //validacion de horas
                                if(arrayHoraFinal[0].toInt()>arrayHoraIncio[0].toInt()){

                                    Guardar()
                                    var  Hora = arrayHoraIncio[0].toInt()
                                    var minutos = arrayHoraIncio[1].toInt()
                                    var day = arrayFechaInicio[0].toInt()
                                    var month = arrayFechaInicio[1].toInt()
                                    var year = arrayFechaInicio[2].toInt()

                                    saveData.SaveData(Hora, minutos, day, month, year)
                                    saveData.setAlarm()
                                }else if(arrayHoraFinal[0].toInt()==arrayHoraIncio[0].toInt()){
                                    //comprobar minutos
                                    if(arrayHoraFinal[1].toInt()>arrayFechaInicio[1].toInt()){
                                        Guardar()
                                        var  Hora = arrayHoraIncio[0].toInt()
                                        var minutos = arrayHoraIncio[1].toInt()
                                        var day = arrayFechaInicio[0].toInt()
                                        var month = arrayFechaInicio[1].toInt()
                                        var year = arrayFechaInicio[2].toInt()

                                        saveData.SaveData(Hora, minutos, day, month, year)
                                        saveData.setAlarm()
                                    }else{
                                        //error
                                        Toast.makeText(this, "Error: porfavor revisa las fechas ingresadas", Toast.LENGTH_LONG).show()
                                    }
                                }else{
                                    //error
                                    Toast.makeText(this, "Error: porfavor revisa las fechas ingresadas", Toast.LENGTH_LONG).show()
                                }
                            }else{
                                //error
                                Toast.makeText(this, "Error: porfavor revisa las fechas ingresadas", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            //error
                            Toast.makeText(this, "Error: porfavor revisa las fechas ingresadas", Toast.LENGTH_LONG).show()
                        }



                    }else{
                        //error
                        Toast.makeText(this, "Error: porfavor revisa las fechas ingresadas", Toast.LENGTH_LONG).show()
                    }

            }else{
                Toast.makeText(this, "Porfavor rellene todos los campos", Toast.LENGTH_SHORT).show()
            }
       }
        btnDashBoard.setOnClickListener{
            var intent = Intent( this, dashBoard::class.java)
            startActivity(intent)
            this.finish()
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

    fun Guardar(){
        val titulo:String = findViewById<EditText>(R.id.etNombre).text.toString()
        val descripcion:String = findViewById<EditText>(R.id.etDescrip).text.toString()
        val horaFin:String = findViewById<TextView>(R.id.tvHoraFin).text.toString()
        val horaInicio:String = findViewById<TextView>(R.id.tvHoraIncio).text.toString()
        val fechaInicio:String = findViewById<TextView>(R.id.tvFechaInicio).text.toString()
        val fechaFin:String = findViewById<TextView>(R.id.tvFechaFin).text.toString()
        val tarea = tarea(titulo, descripcion, horaInicio, horaFin, fechaInicio, fechaFin)
        val database = DbHelper(this)
        database.IngresarTarea(tarea)


        Handler().postDelayed({
            var intent = Intent( this, dashBoard::class.java)
            startActivity(intent)
            this.finish()
        }, 200)
    }


}