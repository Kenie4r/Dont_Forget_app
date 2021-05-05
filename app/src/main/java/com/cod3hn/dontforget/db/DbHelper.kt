package com.cod3hn.dontforget.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.cod3hn.dontforget.`class`.*
import com.cod3hn.dontforget.crearTareas
import java.security.AccessControlContext

//Creación de base de datos
//se crean las contantes que se usaran
val NOMBRE_BASE_DATOS: String = "DontForget"
val NOMBRE_TABLA1:String = "usuario"
val NOMBRE_TABLA2:String = "tarea"
val TABLA1_DATOS: String = "\"ID\"\t INTEGER, \"Nombre\"\tTEXT NOT NULL,\n" +
        "\t\"PIN\"\tINTEGER,\n\tPRIMARY KEY(\"ID\" AUTOINCREMENT)"
val TABLA2_DATOS:String = "\"ID\"\tINTEGER,\n\t\"titulo\"\tTEXT NOT NULL,\n" +
        "\t\"descripcion\"\tTEXT NOT NULL,\n\t\"horaIncio\"\tTEXT NOT NULL,\n" +
        "\t\"horaFinal\"\tTEXT NOT NULL,\n\t\"fechaInicio\"\tTEXT NOT NULL,\n" +
        "\t\"fechaFinal\"\tTEXT NOT NULL,\n\t\"repetir\"\tINTEGER DEFAULT 1,\n" +
        "\t\"completado\"\tTEXT DEFAULT 'No',\n\tPRIMARY KEY(\"ID\" AUTOINCREMENT)"
val DATABASE_VERSION:Int = 3;

//Columnas TABLA tareas
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
    }
/*
    fun insertar (usuario:List<usuario>){
        ContentValues().put(querys.usuarios.COLUMN_NOMBRE, )
    }
*/

    fun IngresarTarea( tarea: tarea){
        var db = writableDatabase
        var cv = ContentValues()
        cv.put(COLUMN_TITULO, tarea.Titulo )
        cv.put(COLUM_DESCRIP, tarea.Descripcion)
        cv.put(COLUMN_HORAOI,tarea.HoraInicio)
        cv.put(COLUMN_HORAF,tarea.HoraFin)
        cv.put(COLUMN_FECHAI,tarea.FechaInicio)
        cv.put(COLUMN_FECHAF,tarea.FechaFinal)
        cv.put(COLUMN_REPETICION,tarea.repeticion)
        cv.put(COLUMN_COMPLETADO,tarea.Completado)

        //hacemos la query
        var result = db?.insert(NOMBRE_TABLA2, null , cv)
        if(result == -1.toLong()){
            Toast.makeText( context , "Error: no sirve", Toast.LENGTH_LONG)

        }else{
            Toast.makeText(context, "Bien", Toast.LENGTH_LONG)
        }
    }
}