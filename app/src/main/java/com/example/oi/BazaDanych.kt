package com.example.oi
//import android.content.Context
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.oi.BasicCommand.DB_NAME

object Table1Info : BaseColumns{
    const val TABLE_NAME = "Napiwki"
    const val TABLE_COLUMN_NAPIWEK = "Napiwek"
    const val TABLE_COLUMN_GODZINY = "Godziny"
    const val TABLE_COLUMN_DATA = "Data"
    const val TABLE_COLUMN_ID_POSADA = "PosadaID"
    const val TABLE_COLUMN_ID_DZIENNIK_AKTYWNOSCI = "DziennikAktywnosciID"
}


object Table2Info : BaseColumns{
    const val TABLE_NAME = "MiejsceZatrudnienia"
    const val TABLE_COLUMN_NAZWA_POSADY = "NazwaPosady"
    const val TABLE_COLUMN_STAWKA = "Stawka"
    const val TABLE_COLUMN_AKTYWNOSC = "Aktywnosc"

}

object Table3Info : BaseColumns{
    const val TABLE_NAME = "DziennikAktywnosci"
    const val TABLE_COLUMN_OPIS = "Opis"


}

object BasicCommand{
    const val DB_NAME = "BAZA_DANYCH"

    const val SQL_CREATE_TABLE1: String =
        "CREATE TABLE IF NOT EXISTS ${Table1Info.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Table1Info.TABLE_COLUMN_NAPIWEK} TEXT NOT NULL," +
                "${Table1Info.TABLE_COLUMN_GODZINY} TEXT NOT NULL," +
                "${Table1Info.TABLE_COLUMN_DATA} TEXT NOT NULL," +
                "${Table1Info.TABLE_COLUMN_ID_POSADA} TEXT NOT NULL," +
                "${Table1Info.TABLE_COLUMN_ID_DZIENNIK_AKTYWNOSCI} TEXT NOT NULL)"

    const val SQL_DELETE_TABLE1 = "DROP TABLE IF EXISTS ${Table1Info.TABLE_NAME}"

    const val SQL_CREATE_TABLE2: String =
        "CREATE TABLE IF NOT EXISTS ${Table2Info.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Table2Info.TABLE_COLUMN_NAZWA_POSADY} TEXT NOT NULL," +
                "${Table2Info.TABLE_COLUMN_STAWKA} TEXT NOT NULL," +
                "${Table2Info.TABLE_COLUMN_AKTYWNOSC} TEXT NOT NULL)"

    const val SQL_DELETE_TABLE2 = "DROP TABLE IF EXISTS ${Table2Info.TABLE_NAME}"

    const val SQL_CREATE_TABLE3: String =
        "CREATE TABLE IF NOT EXISTS ${Table3Info.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Table3Info.TABLE_COLUMN_OPIS} TEXT NOT NULL)"


    const val SQL_DELETE_TABLE3 = "DROP TABLE IF EXISTS ${Table3Info.TABLE_NAME}"
}
class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME ,null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE1)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE2)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE3)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        onCreate(db)


    }

    public fun dropnijTym(db: SQLiteDatabase?){
        db?.execSQL("DROP TABLE ${Table1Info.TABLE_NAME}")
        db?.execSQL("DROP TABLE ${Table2Info.TABLE_NAME}")
        db?.execSQL("DROP TABLE ${Table3Info.TABLE_NAME}")
    }

}
