package com.cod3hn.dontforget

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cod3hn.dontforget.db.COLUMN_ID
import com.cod3hn.dontforget.db.DbHelper
import com.cod3hn.dontforget.db.NOMBRE_TABLA2
import com.cod3hn.dontforget.fragments.DatePickerFragment
import com.cod3hn.dontforget.fragments.timepickerfragment
import kotlinx.android.synthetic.main.activity_crear_tareas.*
import kotlinx.android.synthetic.main.activity_crear_tareas.btnDashBoard
import kotlinx.android.synthetic.main.activity_crear_tareas.etNombre
import kotlinx.android.synthetic.main.activity_edit_task.*
import org.w3c.dom.Text

class edit_task( /*var numeroID: Int*/) : AppCompatActivity( ) {
    private lateinit var DbHp : DbHelper
    private  lateinit var db : SQLiteDatabase
  //  private  var  id :Int = numeroID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

      val passedid = intent.getStringExtra("_ID")
      DbHp= DbHelper(this)
      db = DbHp.readableDatabase

      var id = passedid.toInt()
      var datos = DbHp.getdata(id)


      var tvTitulo:EditText  = findViewById(R.id.etNombreEdit)
      var tvDescrip: EditText = findViewById(R.id.etDescripEdit)
      var tvHoraInicio: TextView = findViewById(R.id.tvHoraInicioEdit)
      var tvHoraFinal: TextView = findViewById(R.id.tvHoraFInEdit)
      var tvDiaInicio: TextView = findViewById(R.id.tvDiaInicioEdit)
      var tvDiaFinal: TextView = findViewById(R.id.tvDiaFinalEdit)
      if(datos!=null){
          tvTitulo.setText( datos.Titulo)
          tvDescrip.setText(datos.Descripcion)
          tvHoraInicio.setText(datos.HoraInicio)
          tvHoraFinal.setText(datos.HoraFin)
          tvDiaInicio.setText(datos.FechaInicio)
          tvDiaFinal.setText(datos.FechaFinal)

      }else{
          var cerrar = Intent(this, tareas::class.java)
          Toast.makeText(this, "ERROR LA TAREA NO EXISTE", Toast.LENGTH_SHORT).show()
          startActivity(cerrar)

      }


        this.btnDashBoard.setOnClickListener(){
            var intent = Intent(this, tareas()::class.java)
            startActivity(intent)
            this.finish()
        }


      this.btnGuardar.setOnClickListener{
          Guardar(id, tvTitulo.text.toString(),tvDescrip.text.toString(), tvHoraInicio.text.toString(),
                  tvHoraFinal.text.toString(), tvDiaInicio.text.toString(), tvDiaFinal.text.toString()  )
          var actividad = Intent(this, tareas::class.java)
          startActivity(actividad)

      }
      this.btnEliminar.setOnClickListener{
          DeleteTask(id)

      }



    }


    //funcion de guardar datos
    fun Guardar(_ID:Int,titulo:String,Descrip:String,HoraI:String, HoraF:String, FechaI:String, FechaF:String ){
        DbHp.updateData(_ID , titulo, Descrip, HoraI,HoraF, FechaI, FechaF)
    }

    //funcion de eliminar la tarea
    fun DeleteTask(_ID: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Tarea")
        builder.setMessage(R.string.Delete_Task)
        //builder.setIcon()
        builder.setPositiveButton(R.string.si_eliminar){
            dialog, wich->
                DbHp.DeleteTask(_ID)
                Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_LONG).show()
                var actividad = Intent(this, tareas::class.java)
                startActivity(actividad)
        }
        builder.setNegativeButton(R.string.No_quiero, null)
        builder.show()
    }





    ///funciones de click para la edición de datos


    fun click(view: View){
        TimePickerOpen()
    }
    fun  TimePickerOpen (){
        val timepicker = timepickerfragment{ OnTimeSelected(it)}
        timepicker.show(supportFragmentManager , "time")
    }

    fun OnTimeSelected (time:String){
        val textView =  findViewById<TextView>(R.id.tvHoraInicioEdit)
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
        val textView =  findViewById<TextView>(R.id.tvHoraFInEdit)
        textView.apply {
            text = "$time"
        }
    }



    //funciones de fragment

    fun DiaInicioClick (view: View){
        CalendarPickerOpen()
    }

    private fun CalendarPickerOpen() {
        val datepicker = DatePickerFragment {day, month, year -> onDateSelectedDay(day, month, year)}
        datepicker.show(supportFragmentManager, "datepicker")
    }

    private fun onDateSelectedDay(day: Int, month: Int, year: Int) {
        val textview = findViewById<TextView>(R.id.tvDiaInicioEdit)

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
        val textview = findViewById<TextView>(R.id.tvDiaFinalEdit)

        textview.apply {
            text = "$day/$month/$year"
        }
    }




}