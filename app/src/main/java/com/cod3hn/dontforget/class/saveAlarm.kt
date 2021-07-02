package com.cod3hn.dontforget.`class`

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.cod3hn.dontforget.AlarmReciver
import com.cod3hn.dontforget.dashBoard
import java.util.*
import kotlin.math.min

class saveAlarm {

    var context:Context?=null
    var sharedRef:SharedPreferences?=null
    constructor(context:Context){
        this.context=context
        sharedRef=context.getSharedPreferences("myref",Context.MODE_PRIVATE)
    }

    fun SaveData(hour:Int,minute:Int, day:Int, mes:Int, year:Int){
        var editor=sharedRef!!.edit()
        editor.putInt("hour",hour)
        editor.putInt("minute",minute)
        editor.putInt("day", day)
        editor.putInt("month",mes)
        editor.putInt("year", year)
        editor.commit()
    }

    fun  getHour():Int{
        return sharedRef!!.getInt("hour",0)
    }
    fun  getMinute():Int{
        return sharedRef!!.getInt("minute",0)
    }
    fun getDay():Int{
        return sharedRef!!.getInt("day",0)
    }
    fun getMonth():Int{
        return sharedRef!!.getInt("month",0)
    }
    fun getYear():Int{
        return sharedRef!!.getInt("year",0)
    }
    fun setAlarm(){
        val hour:Int=getHour()
        val minute:Int=getMinute()
        val day:Int = getDay()
        val month:Int = getMonth()
        val year:Int= getYear()
        val calender=Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY,hour)
        calender.set(Calendar.MINUTE,minute)
        calender.set(Calendar.SECOND,0)
        calender.set(Calendar.YEAR, year)
        calender.set(Calendar.DAY_OF_MONTH, day)
        calender.set(Calendar.MONTH, month)
        //calender.set(Calendar.DAY_OF_MONTH)

        val am= context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var intent=Intent(context,AlarmReciver::class.java)
        intent.putExtra("message","Una de tus tareas está por comenzar")
        intent.action="com.cod3hn.dontforget"
        val pi=PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)


        am.setRepeating(AlarmManager.RTC_WAKEUP,calender.timeInMillis,
                AlarmManager.INTERVAL_DAY,pi)



    }
}