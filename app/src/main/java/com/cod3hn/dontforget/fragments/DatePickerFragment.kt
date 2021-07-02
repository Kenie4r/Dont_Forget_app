package com.cod3hn.dontforget.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment (val listener: (day:Int ,month:Int ,year:Int )->Unit ):DialogFragment(),DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val dia = calendar.get(Calendar.DAY_OF_MONTH)
        val mes = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val picker= DatePickerDialog(activity as Context, this, year, mes, dia )
        return picker
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
       listener(dayOfMonth, month, year)
    }


}