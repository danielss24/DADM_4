package com.example.cuatroenraya.model

import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import java.io.File

object RoundRepository {
    val rounds = ArrayList<Round>()
    init {

        for (i in 0..100)
            rounds.add(Round(stringTablero = "111110111110111110111110111110111110111110", nombrePartida = "nombrePartida"))

        val jugadores = arrayListOf<Jugador>()
        val tableroMain = TableroConecta4()

//        val path = File("./saves")
//        println("Estos son las partidas guardadas, elige el nombre para cargar esa partida")
//        for (archivo in path.list()){
//            println("\t-> $archivo")
//        }
//
//        print("Fichero a cargar:")
//        var fichero = readLine()!!.toString()
//        var ficheroString = ""
//        var flagFichero = 0
//        var fileToLoad =  File("src/Main.kt")
//
//        while (flagFichero==0){
//            if(File("saves/$fichero.txt").exists()){
//                ficheroString = File("saves/$fichero.txt").readText()
//                fileToLoad = File("saves/$fichero.txt")
//                flagFichero = 1
//            }else if (File("saves/$fichero").exists()){
//                ficheroString = File("saves/$fichero").readText()
//                fileToLoad = File("saves/$fichero")
//                flagFichero = 1
//            }else{
//                println("Fichero no correcto")
//                print("Introduzca el fichero a fichero a cargar:")
//                fichero = readLine()!!.toString()
//            }
//        }
//
//        tableroMain.cargaTablero(ficheroString)
//        fileToLoad.forEachLine {
//            val linea = it
//            if((it.contains("Jugador1"))){
//                val splited = linea.split(":")
//                val nombreJg1 = splited[1].trim()
//                jugadores += JugadorConecta4(nombreJg1)
//
//            }else if((it.contains("Jugador2"))){
//                val splited = linea.split(":")
//                val nombreJg1 = splited[1].trim()
//                if(nombreJg1 == "Maquina"){
//                    jugadores += JugadorAleatorio(nombreJg1)
//                }else{
//                    jugadores += JugadorConecta4(nombreJg1)
//                }
//            }
//        }

    }
    fun getRound(id: String): Round {
        val round = rounds.find { it.id == id }
        return round ?: throw Exception("Round not found.")
    }
}
