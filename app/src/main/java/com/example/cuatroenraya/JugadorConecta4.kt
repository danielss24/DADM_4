package com.example.cuatroenraya
import android.support.design.widget.Snackbar
import android.view.View
import es.uam.eps.multij.*
import java.io.File
import java.io.FileNotFoundException

/**
 *
 * @author mfreire
 */
class JugadorConecta4
constructor(private val nombre: String) : View.OnClickListener, Jugador {

    val ids = arrayOf(intArrayOf(
        R.id.c11,
        R.id.c12,
        R.id.c13,
        R.id.c14,
        R.id.c15,
        R.id.c16,
        R.id.c17
    ),
        intArrayOf(
            R.id.c21,
            R.id.c22,
            R.id.c23,
            R.id.c24,
            R.id.c25,
            R.id.c26,
            R.id.c27
        ),
        intArrayOf(
            R.id.c31,
            R.id.c32,
            R.id.c33,
            R.id.c34,
            R.id.c35,
            R.id.c36,
            R.id.c37
        ),
        intArrayOf(
            R.id.c41,
            R.id.c42,
            R.id.c43,
            R.id.c44,
            R.id.c45,
            R.id.c46,
            R.id.c47
        ),
        intArrayOf(
            R.id.c51,
            R.id.c52,
            R.id.c53,
            R.id.c54,
            R.id.c55,
            R.id.c56,
            R.id.c57
        ),
        intArrayOf(
            R.id.c61,
            R.id.c62,
            R.id.c63,
            R.id.c64,
            R.id.c65,
            R.id.c66,
            R.id.c67
        )
    )

    private lateinit var game: Partida

    fun setPartida(game: Partida) {
        this.game = game
    }
    override fun onClick(v: View) {
        try {
            if (game.tablero.estado != Tablero.EN_CURSO) {
                //TODO
                // Comprobar la cadena
                //Snackbar.make(v, R.string.round_already_finished,Snackbar.LENGTH_SHORT).show()
                Snackbar.make(v, "round_already_finished", Snackbar.LENGTH_SHORT).show()
                return
            }
            //TODO
            val m: Movimiento = MovimientoConecta4(fromViewToJ(v))
            val a = AccionMover(this, m)
            game.realizaAccion(a)
        } catch (e: Exception) {
            //TODO
            // Comprobar la cadena
            //Snackbar.make(v, R.string.invalid_movement,Snackbar.LENGTH_SHORT).show()
            Snackbar.make(v, "invalid_movement", Snackbar.LENGTH_SHORT).show()
        }
    }

    /*override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_FIN, Evento.EVENTO_CAMBIO -> {
                return
            }
            Evento.EVENTO_CONFIRMA -> {
                //TODO a lo mejor habría que pòner un finally en las exception para hacer algo siempre y que no se cuelgue
                try {
                    evento.partida.confirmaAccion(
                            this, evento.causa, Math.random() > .5)
                } catch (e: Exception) {
                    throw ExcepcionJuego("Error en la confirmación de la acción")
                }
            }
            Evento.EVENTO_TURNO -> {
                print("\n Elige columna ${evento.partida.getJugador(evento.partida.tablero.turno).nombre}: \n")
                val r = readLine()!!.toInt()
                val movimientoJugador = MovimientoConecta4(r)
                try {
                    if (r == 8){
                        val path = File("./saves")
                        println("Estos son las partidas guardadas, elige el nombre para guardar esta partida\n" +
                                "Si ya existe, la partida se sobreecribirá.")
                        for (archivo in path.list()){
                            println("\t-> " + archivo)
                        }
                        print("\nNombre:")
                        val nombreFichero = readLine()!!.toString() + ".txt"
                        if (path.mkdirs()){
                            println("Se ha creado")
                        } else {
                            println("Ya esta creado")
                        }
                        val fichero = File(path, nombreFichero)
                        try {
                            var TurnoActual : String
                            if (this.nombre.equals(evento.partida.getJugador(0))){
                                TurnoActual = this.nombre
                            } else  {
                                TurnoActual = evento.partida.getJugador(0).nombre
                            }
                            var tableroTMP = evento.partida.tablero
                            if(tableroTMP is TableroConecta4){
                                var tableroArray = tableroTMP.imprimeTablero()
                                fichero.writeText("Turno: ${evento.partida.tablero.turno}\n" +
                                        "NumJugadas: ${evento.partida.tablero.numJugadas}\n" +
                                        "Mueve: " + TurnoActual + "\n" +
                                        "Jugador1: ${evento.partida.getJugador(0).nombre}\n" +
                                        "Jugador2: ${evento.partida.getJugador(1).nombre}\n" +
                                        "Tablero String: ${tableroArray}\n" +
                                        "Tablero Grafico: ${evento.partida.tablero}")
                            }
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                        //evento.partida.tablero.estado = Tablero.FINALIZADA
                    } else if (r == 9){
                        //evento.partida.tablero.estado = Tablero.FINALIZADA
                        print("Hasta luego!")
                    } else {
                        evento.partida.realizaAccion(AccionMover(this, movimientoJugador))
                    }
                } catch (e: Exception) {
                    throw ExcepcionJuego("Error en al elegir la columna")
                }

            }
        }

    }
*/
    /**
     * Devuelve el nombre del jugador
     */
    /*override fun getNombre(): String {
        return nombre
    }*/

    /**
     * Este jugador juega *todos* los juegos
     */
    /*override fun puedeJugar(tablero: Tablero): Boolean {
        if (tablero is TableroConecta4){
            return true
        } else {
            return false
        }
    }*/
    override fun getNombre() = "ER local player"
    override fun puedeJugar(p0: Tablero?) = true
    override fun onCambioEnPartida(p0: Evento) {}
    private fun fromViewToI(view: View): Int {
        for (i in 0 until ids.size)
            for (j in 0 until ids[i].size) {
                if (view.id == ids[i][j])
                    return i
            }
        return -1
    }
    private fun fromViewToJ(view: View): Int {
        for (i in 0 until ids.size)
            for (j in 0 until ids[i].size)
                if (view.id == ids[i][j])
                    return j
        return -1
    }

}
