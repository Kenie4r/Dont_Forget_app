package com.cod3hn.dontforget.db

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.service.autofill.FillEventHistory
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.cod3hn.dontforget.`class`.*
import com.cod3hn.dontforget.crearTareas
import com.cod3hn.dontforget.tareas
import kotlinx.coroutines.delay
import java.security.AccessControlContext

//Creación de base de datos
//se crean las contantes que se usaran
val NOMBRE_BASE_DATOS: String = "DontForget"
val NOMBRE_TABLA1:String = "usuario"
val NOMBRE_TABLA2:String = "tarea"
val TABLA1_DATOS: String = "\"ID\"\t INTEGER, \"Nombre\"\tTEXT NOT NULL,\n" +
        "\t\"PIN\"\tINTEGER,\n\tPRIMARY KEY(\"ID\" AUTOINCREMENT)"
val TABLA2_DATOS:String = "\"ID\"\tINTEGER,\n\t\"titulo\"\tTEXT NOT NULL,\n" +
        "\t\"descripcion\"\tTEXT NOT NULL,\n\t\"horaInicio\"\tTEXT NOT NULL,\n" +
        "\t\"horaFinal\"\tTEXT NOT NULL,\n\t\"fechaInicio\"\tTEXT NOT NULL,\n" +
        "\t\"fechaFinal\"\tTEXT NOT NULL,\n\t\"repetir\"\tINTEGER DEFAULT 1,\n" +
        "\t\"completado\"\tTEXT DEFAULT 'No',\n\tPRIMARY KEY(\"ID\" AUTOINCREMENT)"
val DATABASE_VERSION:Int = 6

//Columnas TABLA tareas
val COLUMN_ID:String = "ID"
val COLUMN_TITULO: String = "titulo"
val COLUM_DESCRIP:String = "descripcion"
val COLUMN_HORAOI:String = "horaInicio"
val COLUMN_HORAF:String = "horaFinal"
val COLUMN_FECHAI:String = "fechaInicio"
val COLUMN_FECHAF:String = "fechaFinal"
val COLUMN_REPETICION:String = "repetir"
val COLUMN_COMPLETADO:String = "completado"

//Clase de la base de datos

class DbHelper(val context: Context ):SQLiteOpenHelper(context, NOMBRE_BASE_DATOS, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTabla1 = "CREATE TABLE "+ NOMBRE_TABLA1+"("+ TABLA1_DATOS+")"
        val crearTabla2 = "CREATE TABLE "+ NOMBRE_TABLA2+"("+ TABLA2_DATOS+")"

        db?.execSQL(crearTabla1)
        db?.execSQL(crearTabla2)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val destroy  = "DROP TABLE $NOMBRE_TABLA1"
        val destroy2 = "DROP TABLE $NOMBRE_TABLA2"
        db?.execSQL(destroy)
        db?.execSQL(destroy2)
        onCreate(db)
    }
/*
    fun insertar (usuario:List<usuario>){
        ContentValues().put(querys.usuarios.COLUMN_NOMBRE, )
    }
*/

    fun IngresarTarea( tarea: tarea){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_TITULO, tarea.Titulo )
        cv.put(COLUM_DESCRIP, tarea.Descripcion)
        cv.put(COLUMN_HORAOI,tarea.HoraInicio)
        cv.put(COLUMN_HORAF,tarea.HoraFin)
        cv.put(COLUMN_FECHAI,tarea.FechaInicio)
        cv.put(COLUMN_FECHAF,tarea.FechaFinal)
        cv.put(COLUMN_REPETICION,tarea.repeticion)
        cv.put(COLUMN_COMPLETADO,tarea.Completado)
        //hacemos la query
        val result = db?.insert(NOMBRE_TABLA2, null , cv)
        if(result == -1.toLong()){
            Toast.makeText( context , "Tarea no creada, vuelve a intentarlo", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(context, "Tarea creada con exito", Toast.LENGTH_LONG).show()
        }
    }
    fun obtenerActivas():Int{
        val db = readableDatabase
        val cv = ContentValues()
        val query = "SELECT * FROM "+ NOMBRE_TABLA2 + " WHERE "+ COLUMN_COMPLETADO+"= 'inProgress' LIMIT 1"

        val result: Cursor = db.rawQuery(query, null)
        return result.count
    }



    fun getdata( theID: Int): tarea {
        val db = readableDatabase
        val cv = ContentValues()
        val query = "SELECT * FROM "+ NOMBRE_TABLA2 + " WHERE "+ COLUMN_ID+ "="+theID+" LIMIT 1"

        val result: Cursor = db.rawQuery(query, null)
        var tarea = tarea()
        if(result.moveToFirst()){
            tarea.Titulo = result.getString(1)
            tarea.Descripcion = result.getString(2)
            tarea.HoraInicio   =result.getString(3)
            tarea.HoraFin = result.getString( 4)
            tarea.FechaInicio = result.getString(5)
            tarea.FechaFinal = result.getString(6)
            tarea.repeticion = result.getString(7)
            tarea.Completado = result.getString(8)
        }
        result.close()
        db.close()
        return tarea
    }

    fun inProgress(_ID: Int):Boolean{
        val  db = writableDatabase
        val  cv = ContentValues()
        val id = _ID.toString()

        cv.put(COLUMN_COMPLETADO, "inProgress")

        val result = db.update(NOMBRE_TABLA2, cv, "ID=?", arrayOf(id))
        if(result != -1){
            Toast.makeText( context , "Tarea actualizada con exito", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "Tarea no pudo ser actualizada", Toast.LENGTH_LONG).show()
        }
        return true
    }
    fun updateData(_ID:Int, titulo:String,Descrip:String,HoraI:String, HoraF:String, FechaI:String, FechaF:String): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        var id = _ID.toString()
        //escribir los valores
        cv.put(COLUMN_TITULO, titulo)
        cv.put(COLUM_DESCRIP, Descrip)
        cv.put(COLUMN_HORAOI, HoraI)
        cv.put(COLUMN_HORAF, HoraF)
        cv.put(COLUMN_FECHAI, FechaI)
        cv.put(COLUMN_FECHAF, FechaF)


        val result = db?.update(NOMBRE_TABLA2,cv,"ID=?",arrayOf(id))
        if(result != -1){
            Toast.makeText( context , "Tarea actualizada con exito", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "Tarea no pudo ser actualizada", Toast.LENGTH_LONG).show()
        }
        return true
    }


    fun DeleteTask(_ID:Int):Int{
        val db = this.writableDatabase

        var id = _ID.toString()
        return db.delete(NOMBRE_TABLA2,"ID = ?", arrayOf(id))
    }

}