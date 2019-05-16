package com.example.cuatroenraya.database

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import android.util.Log
import com.example.cuatroenraya.model.RoundRepository
import com.example.cuatroenraya.database.RoundCursorWrapper
import com.example.cuatroenraya.database.RoundDataBaseSchema
import com.example.cuatroenraya.model.Round
import java.util.*

/**
 * @brief clase para la base de datos local
 * @param context contexto
 */
class DataBase(context: Context) : RoundRepository {
    private val DEBUG_TAG = "DEBUG"
    private val helper: DatabaseHelper
    private var db: SQLiteDatabase? = null

    init {
        helper = DatabaseHelper(context)
    }

    /**
     * @brief Clase con utilidades para la base de datos
     * @param context contexto
     */
    private class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        /**
         * @brief funcion creadora de la base de datos
         * @param db base de datos
         */
        override fun onCreate(db: SQLiteDatabase) {
            createTable(db)
        }


        /**
         * @brief funcion para crear las tablas de la base de datos
         * @param db base de datos
         */
        private fun createTable(db: SQLiteDatabase) {
            val str1 = ("CREATE TABLE " + RoundDataBaseSchema.UserTable.NAME + " ("
                    + "_id integer primary key autoincrement, "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERUUID + " TEXT UNIQUE, "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERNAME + " TEXT UNIQUE, "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERPASSWORD + " TEXT);")
            val str2 = ("CREATE TABLE " + RoundDataBaseSchema.RoundTable.NAME + " ("
                    + "_id integer primary key autoincrement, "
                    + RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID + " TEXT UNIQUE, "
                    + RoundDataBaseSchema.RoundTable.Cols.PLAYERUUID + " TEXT REFERENCES "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERUUID + ", "
                    + RoundDataBaseSchema.RoundTable.Cols.DATE + " TEXT, "
                    + RoundDataBaseSchema.RoundTable.Cols.TITLE + " TEXT, "
                    + RoundDataBaseSchema.RoundTable.Cols.BOARD + " TEXT);")
            try {
                db.execSQL(str1)
                db.execSQL(str2)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        /**
         * @brief funcion para actualizar la base de datos
         * @param db base de datos
         * @param oldVersion version antigua
         * @param newVersion version nueva
         *
         */
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + RoundDataBaseSchema.UserTable.NAME)
            db.execSQL("DROP TABLE IF EXISTS " + RoundDataBaseSchema.RoundTable.NAME)
            createTable(db)
        }

    }

    @Throws(SQLException::class)
    override fun open() {
        db = helper.writableDatabase
    }

    /**
     * @brief funcion para cerrar la base de datos
     */
    override fun close() {
        db?.close()
    }

    /**
     * @brief funcion para loggearnos en funcion de la base de datos
     * @param playername nombre del jugador
     * @param playerpassword contraseña
     * @param callback callback
     */
    override fun login(playername: String, playerpassword: String, callback:RoundRepository.LoginRegisterCallback
    ) {
        Log.d(DEBUG_TAG, "Login $playername")
        val cursor = db!!.query(
            RoundDataBaseSchema.UserTable.NAME,
            arrayOf(RoundDataBaseSchema.UserTable.Cols.PLAYERUUID),
            RoundDataBaseSchema.UserTable.Cols.PLAYERNAME + " = ? AND "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERPASSWORD + " = ?",
            arrayOf(playername, playerpassword),
            null, null, null
        )
        val count = cursor.count
        val uuid =
            if (count == 1 && cursor.moveToFirst()) cursor.getString(0) else ""
        cursor.close()
        if (count == 1)
            callback.onLogin(uuid)
        else
            callback.onError("Username or password incorrect.")
    }

    /**
     * @brief funcion para insertar un nuevo usuario en la base de datos
     * @param playername nombre del jugador
     * @param playerpassword contraseña
     * @param callback callback
     */
    override fun register(
        playername: String, password: String, callback:
        RoundRepository.LoginRegisterCallback
    ) {
        val values = ContentValues()
        val uuid = UUID.randomUUID().toString()
        values.put(RoundDataBaseSchema.UserTable.Cols.PLAYERUUID, uuid)
        values.put(RoundDataBaseSchema.UserTable.Cols.PLAYERNAME, playername)
        values.put(RoundDataBaseSchema.UserTable.Cols.PLAYERPASSWORD, password)
        val id = db!!.insert(RoundDataBaseSchema.UserTable.NAME, null, values)
        if (id < 0)
            callback.onError("Error inserting new player named $playername")
        else
            callback.onLogin(uuid)
    }

    /**
     * @brief funcion para añadir una ronda a la base de datos
     * @param round ronda
     * @param callback callback
     */
    override fun addRound(round: Round, callback: RoundRepository.BooleanCallback) {
        val values = getContentValues(round)
        val id = db!!.insert(RoundDataBaseSchema.RoundTable.NAME, null, values)
        callback.onResponse(id >= 0)
    }

    /**
     * @brief Funcion para añadir los valores de una ronda a la base de datos
     * @param round ronda
     * @return los valores de la ronda
     */
    private fun getContentValues(round: Round): ContentValues {
        val values = ContentValues()
        values.put(RoundDataBaseSchema.RoundTable.Cols.PLAYERUUID, round.firstPlayerUUID)
        values.put(RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID, round.id)
        values.put(RoundDataBaseSchema.RoundTable.Cols.DATE, round.date)
        values.put(RoundDataBaseSchema.RoundTable.Cols.TITLE, round.title)
        values.put(RoundDataBaseSchema.RoundTable.Cols.BOARD, round.board.tableroToString())
        return values
    }

    /**
     * @brief funcion para actualizar una ronda en la base de datos
     * @param round ronda
     * @param callback callback
     */
    override fun updateRound(round: Round, callback: RoundRepository.BooleanCallback) {
        val values = getContentValues(round)
        val id = db!!.update(
            RoundDataBaseSchema.RoundTable.NAME, values,
            RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID + " = ?",
            arrayOf(round.id)
        )
        callback.onResponse(id >= 0)
    }

    /**
     * @brief funcion que ensambla una query para obtener las rondas de un jugador
     * @return La query
     */
    private fun queryRounds(): RoundCursorWrapper? {
        val sql = "SELECT " + RoundDataBaseSchema.UserTable.Cols.PLAYERNAME + ", " +
                RoundDataBaseSchema.UserTable.Cols.PLAYERUUID + ", " +
                RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID + ", " +
                RoundDataBaseSchema.RoundTable.Cols.DATE + ", " +
                RoundDataBaseSchema.RoundTable.Cols.TITLE + ", " +
                RoundDataBaseSchema.RoundTable.Cols.BOARD + " " +
                "FROM " + RoundDataBaseSchema.UserTable.NAME + " AS p, " +
                RoundDataBaseSchema.RoundTable.NAME + " AS r " +
                "WHERE " + "p." + RoundDataBaseSchema.UserTable.Cols.PLAYERUUID + "=" +
                "r." + RoundDataBaseSchema.RoundTable.Cols.PLAYERUUID + ";"
        val cursor = db!!.rawQuery(sql, null)
        return RoundCursorWrapper(cursor)
    }

    companion object {
        private val DATABASE_NAME = "conectacuatro.db"
        private val DATABASE_VERSION = 1
    }


    /**
     * @brief funcion para obtenner las rondas de un jugador
     * @param playeruuid id del jugador
     * @param orderByField campo por el que ordenar
     * @param group grupo
     * @param callback calbback
     */
    override fun getRounds(
        playeruuid: String, orderByField: String, group: String,
        callback: RoundRepository.RoundsCallback
    ) {
        val rounds = ArrayList<Round>()
        val cursor = queryRounds()

        cursor!!.moveToFirst()
        while (!cursor.isAfterLast()) {
            val round = cursor.round
            if (round.firstPlayerUUID.equals(playeruuid))
                rounds.add(round)
            cursor.moveToNext()
        }
        cursor.close()
        if (cursor.getCount() >= 0)
            callback.onResponse(rounds)
        else
            callback.onError("No rounds found in database")
    }

    override fun deleteRound(round: Round, callback: RoundRepository.BooleanCallback): Boolean {
        print("TODO")
        val sql = "DELETE FROM " + RoundDataBaseSchema.RoundTable.NAME + " WHERE " + RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID + " =  \"" + round.id + "\";"
        val cursor = db!!.delete(RoundDataBaseSchema.RoundTable.NAME,RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID + "=?", arrayOf(round.id.toString()))
        callback.onResponse(cursor >= 0)
        return true
    }
}