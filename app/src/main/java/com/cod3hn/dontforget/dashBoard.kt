package com.cod3hn.dontforget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.health.TimerStat
import android.view.View
import com.cod3hn.dontforget.`class`.PreUtil
import com.cod3hn.dontforget.`class`.notificacion
import com.cod3hn.dontforget.fragments.menu
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


    private var segundosrestantes: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

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
        btnQuit.setOnClickListener(){
            v->
            timer.cancel()
            onTimerFinished()
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
                btnQuit.isEnabled  =true
            }
            TimerState.Paused->{
                btnPlay.isEnabled = true
                btnPause.isEnabled = false
                btnQuit.isEnabled  =true
            }
            TimerState.Stopped->{
                btnPlay.isEnabled = true
                btnPause.isEnabled = false
                btnQuit.isEnabled  =false
            }
        }
    }

    fun crearTask( view: View){
        var abrir = Intent( this, crearTareas::class.java)
        startActivity(abrir)

    }

    fun verTasks(view: View){
        val intent = Intent(this, tareas::class.java)
        startActivity(intent)
    }


}