package com.cod3hn.dontforget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.health.TimerStat
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cod3hn.dontforget.`class`.*
import com.cod3hn.dontforget.databinding.ActivityDashBoardBinding
import com.cod3hn.dontforget.databinding.ActivityTareasBinding
import com.cod3hn.dontforget.databinding.CardviewIniciadasBinding
import com.cod3hn.dontforget.db.DbHelper
import kotlinx.android.synthetic.main.activity_dash_board.*
import java.sql.Time
import java.util.*


class dashBoard : AppCompatActivity() {
    enum class  TimerState{
        Stopped, Paused, Running
    }

    private lateinit var  timer:CountDownTimer
    private var segundosTimer: Long = 0
    private  var timerState = TimerState.Stopped
    private  lateinit var binding : ActivityDashBoardBinding
    private lateinit var DbHp : DbHelper
    private  lateinit var db : SQLiteDatabase

    private var segundosrestantes: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        //REcicler view
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DbHp= DbHelper(this)

        db = DbHp.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM tarea  WHERE completado = 'inProgress'", null)

        val adaptador = adaptador_iniciadas()


        adaptador.adaptador_iniciadas(this, cursor)

        binding.rvIniciadas.layoutManager = LinearLayoutManager(this )

        binding.rvIniciadas.adapter = adaptador







        //btns
        btnPlay.setOnClickListener(){
            v->
            startTimer()
            timerState = TimerState.Running
            actualizarBotones()
        }
        btnPause.setOnClickListener(){
            v->
            timer.cancel()
            timerState = TimerState.Paused
            actualizarBotones()
        }
    }


    companion object{
        fun setAlarm( context: Context, nowSeconds: Long, secondsRemaining: Long):Long{
            val wakeupTime = (nowSeconds + secondsRemaining ) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent( context, TimerExpiredReciver()::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeupTime, pendingIntent)
            PreUtil.setAlarmSetTime(nowSeconds , context)
            return wakeupTime
        }
        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerExpiredReciver()::class.java)
            val intentpendiente = PendingIntent.getBroadcast(context, 0 , intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(intentpendiente)
            PreUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis/1000

    }






    override  fun onResume(){
        super.onResume()

        initTimer()
        removeAlarm(this)
        notificacion.hideTimerNotification(this)
    }
    override  fun onPause(){
        super.onPause()

        if(timerState == TimerState.Running){
            timer.cancel()
            val wakeupTIme = setAlarm(this, nowSeconds, segundosrestantes)
            //seguir con el timer en segundo plano y mostrar notificacion
            notificacion.showTimerRunning(this, wakeupTIme)
        }else if(timerState == TimerState.Paused){
            //mostrar la notificacion
            notificacion.showTimerPaused(this)
        }

        PreUtil.setPreviousTimerLengthSeconds(segundosTimer, this)
        PreUtil.setSecondsRemaining(segundosrestantes, this)
        PreUtil.setTimerState(timerState, this)
    }

    private fun initTimer(){
        timerState = PreUtil.getTimerState(this)
        if(timerState == TimerState.Stopped)
            setNewTimerLegnth()
        else
            setPreviousTimerLegth()

        segundosrestantes = if(timerState=== TimerState.Running || timerState == TimerState.Paused)
            PreUtil.getSecondsRemaining(this )
        else
            segundosTimer

        //segundo plano
        val alarmSetTime = PreUtil.getAlarmTime(this)
        if(alarmSetTime>0){
            segundosrestantes -= nowSeconds- alarmSetTime
        }
        if (segundosrestantes<=0){
            onTimerFinished()
        }else
        if(timerState == TimerState.Running){
            startTimer()
        }
        actualizarBotones()
        updateCountdownUI()
    }
    private fun onTimerFinished(){
        timerState = TimerState.Stopped
        //var _ID = tiempo(this).ObtenerID()
        //DbHelper(this).finished(_ID)
        setNewTimerLegnth()

        materialProgressBar.progress = 0

        PreUtil.setSecondsRemaining(segundosTimer, this)

        actualizarBotones()
        updateCountdownUI()
    }
    fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(segundosrestantes*1000, 1000){
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                segundosrestantes = millisUntilFinished/1000
                updateCountdownUI()
            }
        }.start()

    }

    private  fun setNewTimerLegnth(){
        val lengthMinutes = PreUtil.getTimerLengtj(this)
        segundosTimer = (lengthMinutes*60L)
        materialProgressBar.max  = segundosTimer.toInt()
    }

    fun setPreviousTimerLegth(){
        segundosTimer = PreUtil.getPreviiousTimerLengthSeconds(this)
        materialProgressBar.max = segundosTimer.toInt()
    }

    fun updateCountdownUI(){
        val minutesUntilFinish = segundosrestantes / 60
        val secondsiInMinuteUntilFinish = segundosrestantes - minutesUntilFinish * 60
        val secondsStr = secondsiInMinuteUntilFinish.toString()
        tvCountDown.text = "$minutesUntilFinish: ${
            if(secondsStr.length == 2) secondsStr
            else "0"+ secondsStr}"
        materialProgressBar.progress = (segundosTimer - segundosrestantes).toInt()
    }
    fun actualizarBotones(){
        when(timerState){
            TimerState.Running->{
                btnPlay.isEnabled = false
                btnPause.isEnabled = true
            }
            TimerState.Paused->{
                btnPlay.isEnabled = true
                btnPause.isEnabled = false
            }
            TimerState.Stopped->{
                btnPlay.isEnabled = true
                btnPause.isEnabled = false
            }
        }
    }

    fun crearTask( view: View){
        var abrir = Intent( this, crearTareas::class.java)
        startActivity(abrir)

        this.finish()

    }

    fun verTasks(view: View){
        var existen = DbHelper(this).obtenerCreadas()
        if(existen>0){
            val intent = Intent(this, tareas::class.java)
            startActivity(intent)
            this.finish()
        }else{
            Toast.makeText(this, "Aún no hay ninguna tarea creada", Toast.LENGTH_SHORT).show()
        }

    }


    fun finalizarTimer(){
        onTimerFinished()
    }


}