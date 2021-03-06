
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.JugadorConecta4
import es.uam.eps.multij.Partida
import java.io.File
import java.lang.Exception

val MENU_STRING = "\t(1)- Un solo jugador\n\t(2)- Multijugador\n\t" +
        "(3)- Cargar ultima partida\n\t(4)- Salir\n\t(5)- Todo PC\nModo:"

fun main(args: Array<String>) {
    val jugadores = arrayListOf<Jugador>()
    val tableroMain = TableroConecta4()
    var flagModo = 0
    var modo = 0

    println("Bienvenido a 4 en raya")
    println("En cualquier momento de la partida introduzca un 8 para guardar partida, 9 para salir sin guardar.")
    println(MENU_STRING)

    while(flagModo==0){
        try {
            modo = readLine()!!.toInt()
            if(modo in 1..5){
                flagModo=1
            }else{
                println("<<$modo>> no se encuentra entre las opciones correctas de ejecuión.")
                println("Seleccione un modo")
                println(MENU_STRING)
            }
        }catch (e: Exception){
            println("La opción introducida no es correcta.")
            println("Seleccione un modo")
            println(MENU_STRING)
        }
    }

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
            val path = File("./saves")
            println("Estos son las partidas guardadas, elige el nombre para cargar esa partida")
            for (archivo in path.list()){
                println("\t-> $archivo")
            }
            print("Fichero a cargar:")
            var fichero = readLine()!!.toString()
            var ficheroString = ""
            var flagFichero = 0
            var fileToLoad =  File("src/Main.kt")

            while (flagFichero==0){
                if(File("saves/$fichero.txt").exists()){
                    ficheroString = File("saves/$fichero.txt").readText()
                    fileToLoad = File("saves/$fichero.txt")
                    flagFichero = 1
                }else if (File("saves/$fichero").exists()){
                    ficheroString = File("saves/$fichero").readText()
                    fileToLoad = File("saves/$fichero")
                    flagFichero = 1
                }else{
                    println("Fichero no correcto")
                    print("Introduzca el fichero a fichero a cargar:")
                    fichero = readLine()!!.toString()
                }
            }
            tableroMain.cargaTablero(ficheroString)
            fileToLoad.forEachLine {
                val linea = it
                if((it.contains("Jugador1"))){
                    val splited = linea.split(":")
                    val nombreJg1 = splited[1].trim()
                    jugadores += JugadorConecta4(nombreJg1)

                }else if((it.contains("Jugador2"))){
                    val splited = linea.split(":")
                    val nombreJg1 = splited[1].trim()
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
        else -> {
            println("Se ha producido un error inesperado, lance de nuevo el programa")
        }
    }

    val partida = Partida(tableroMain, jugadores)
    partida.addObservador(ObservadorConecta4())
    partida.comenzar()


}