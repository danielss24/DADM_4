package com.example.cuatroenraya.model

import es.uam.eps.multij.Evento
import es.uam.eps.multij.PartidaListener

/**
 * @brief Observador de la partida
 */
class ObservadorConecta4 @JvmOverloads constructor() : PartidaListener {

    var turno: Int = 1
    /**
     * @brief cambio de partida
     * @param evento evento sobre partida
     */
    override fun onCambioEnPartida(evento: Evento) {
        var tableroTMP = evento.partida.tablero
        if (tableroTMP is TableroConecta4) {
            when (evento?.tipo) {
                Evento.EVENTO_CAMBIO, Evento.EVENTO_FIN -> {
                    print(tableroTMP.imprimeTablero())
                    print(evento.descripcion)
                }
                Evento.EVENTO_ERROR -> {
                    print(tableroTMP.imprimeTablero())
                    print(evento.descripcion)
                }
            }
        }
    }
}
