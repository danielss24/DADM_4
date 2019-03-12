package com.example.cuatroenraya
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.Partida
import java.io.File

fun main(args: Array<String>) {
    val jugadores = arrayListOf<Jugador>()
    var stringTablero = ""
    var flagCargar = 0
    var tableroMain = TableroConecta4()

    println("Bienvenido a 4 en raya")
    println("En cualquier momento de la partida introduzca un 8 para guardar partida, 9 para salir sin guardar.")
    println("\t(1)- Un solo jugador")
    println("\t(2)- Multijugador")
    println("\t(3)- Cargar ultima partida")
    println("\t(4)- Salir")
    println("\t(5)- Todo PC")
    print("Modo:")
    val modo = readLine()!!.toInt()

    when (modo) {
        1 -> {
            println("Introduzca su nombre:")
            val nombreJg1 = readLine()!!.toString()
            jugadores += JugadorConecta4(nombreJg1)
            jugadores += JugadorAleatorio("Maquina")
        }
        2 -> {
            println("Introduzca su nombre jugador1:")
            var nombreJg1 = readLine()!!.toString()
            jugadores += JugadorConecta4(nombreJg1)
            println("Introduzca su nombre jugador2:")
            nombreJg1 = readLine()!!.toString()
            jugadores += JugadorConecta4(nombreJg1)

        }
        3 -> {
            flagCargar = 1
            val path = File("./saves")
            println("Estos son las partidas guardadas, elige el nombre para cargar esa partida\n")
            for (archivo in path.list()){
                println("\t-> " + archivo)
            }
            print("Fichero:")
            var fichero = readLine()!!.toString()
            var ficheroString = File("saves/$fichero.txt").readText()

            tableroMain.stringToTablero(ficheroString)
            File("saves/$fichero.txt").forEachLine {
                val linea = it
                if((it.contains("Jugador1"))){
                    var splited = linea.split(":")
                    var nombreJg1 = splited[1].trim()
                    jugadores += JugadorConecta4(nombreJg1)

                }else if((it.contains("Jugador2"))){
                    var splited = linea.split(":")
                    var nombreJg1 = splited[1].trim()
                    if(nombreJg1 == "Maquina"){
                        jugadores += JugadorAleatorio(nombreJg1)
                    }else{
                        jugadores += JugadorConecta4(nombreJg1)
                    }

                }
            }
        }
        4 -> {
            return
        }
        5 -> {
            jugadores += JugadorAleatorio("Aleatorio1")
            jugadores += JugadorAleatorio("Aleatorio2")

        }
    }

    val partida = Partida(tableroMain, jugadores)
    partida.addObservador(ObservadorConecta4())
    partida.comenzar()


}