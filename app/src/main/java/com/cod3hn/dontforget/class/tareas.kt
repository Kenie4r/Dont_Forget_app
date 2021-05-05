package com.cod3hn.dontforget.`class`


class tarea{
    var Titulo: String = ""
    var Descripcion:String = ""
    var HoraInicio:String = ""
    var HoraFin:String = ""
    var FechaInicio:String = ""
    var FechaFinal:String = ""
    var repeticion:String = "1"
    var Completado:String = "no"

    //constructor
    constructor(titulo: String, descripcion:String , horaInicio:String, horaFin:String ,
                fechaInicio:String, fechaFinal:String ){
        this.Titulo  =titulo
        this.Descripcion  = descripcion
        this.HoraInicio = horaInicio
        this.HoraFin = horaFin
        this.FechaInicio = fechaInicio
        this.FechaFinal = fechaFinal
    }
}