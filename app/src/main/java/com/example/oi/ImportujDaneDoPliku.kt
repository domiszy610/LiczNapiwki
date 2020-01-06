package com.example.oi

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.Toast
import java.io.FileWriter
import java.io.IOException
import java.util.Arrays

private val CSV_HEADER = "Id,Data,Liczba godzin,Posada,Stawka,Napiwek,Opis z dziennika aktywnosci"

class ImportujDaneDoPliku : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_importuj_dane_do_pliku)

        var id = ""
        var data = ""
        var posada = ""
        var godziny = ""
        var napiwek = ""
        var stawka = ""
        var opis =""

        var bEks = findViewById(R.id.bEks) as Button
            try {
                bEks.setOnClickListener {
                    val dbHelper = DataBaseHelper(applicationContext)
                    val db = dbHelper.writableDatabase

                    try {

                        val cursor1: Cursor = db.rawQuery(
                            "SELECT ${Table1Info.TABLE_NAME}.${BaseColumns._ID}, ${Table1Info.TABLE_COLUMN_DATA}, ${Table1Info.TABLE_COLUMN_NAPIWEK}, ${Table2Info.TABLE_COLUMN_NAZWA_POSADY}, ${Table2Info.TABLE_COLUMN_STAWKA}, ${Table1Info.TABLE_COLUMN_GODZINY} FROM ${Table1Info.TABLE_NAME} " +
                                    "INNER JOIN ${Table2Info.TABLE_NAME} ON ${Table1Info.TABLE_COLUMN_ID_POSADA} = ${Table2Info.TABLE_NAME}.${BaseColumns._ID}",
                            null
                        )

                        val cursor2: Cursor = db.rawQuery(
                            "SELECT ${Table3Info.TABLE_COLUMN_OPIS}  FROM ${Table1Info.TABLE_NAME} " +
                                    "INNER JOIN ${Table3Info.TABLE_NAME} ON ${Table1Info.TABLE_COLUMN_ID_DZIENNIK_AKTYWNOSCI} = ${Table3Info.TABLE_NAME}.${BaseColumns._ID}",
                            null
                        )

                        if (cursor1 != null) {
                            cursor1.moveToFirst()
                            id = cursor1.getString(0)
                            data = cursor1.getString(1)
                            posada = cursor1.getString(3)
                            godziny = cursor1.getString(5)
                            napiwek = cursor1.getString(2)
                            stawka = cursor1.getString(4)





                            for (i in 1..cursor1.getCount() - 1) {
                                cursor1.moveToNext()

                                id += " ${cursor1.getString(0)}"
                                data += " ${cursor1.getString(1)}"
                                posada += " ${cursor1.getString(3)}"
                                godziny += " ${cursor1.getString(5)}"
                                napiwek += " ${cursor1.getString(2)}"
                                stawka += " ${cursor1.getString(4)}"


                            }

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Nic nie ma w bazie danych w polu posada!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (cursor2 != null) {
                            cursor2.moveToFirst()
                            opis = cursor2.getString(0)






                            for (i in 1..cursor2.getCount() - 1) {
                                cursor2.moveToNext()

                                opis += " ${cursor2.getString(0)}"


                            }

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Nic nie ma w bazie danych w polu posada!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        var Id = id.split(" ").toTypedArray()
                        var Daty = data.split(" ").toTypedArray()
                        var Godziny = godziny.split(" ").toTypedArray()
                        var Napiwki = napiwek.split(" ").toTypedArray()
                        var Stawki = stawka.split(" ").toTypedArray()
                        var Posady = posada.split(" ").toTypedArray()
                        var Opisy = opis.split(" ").toTypedArray()


                        var fileWriter: FileWriter? = null

                        var z1 =
                            Zapis(
                                Id[0],
                                Daty[0],
                                Godziny[0],
                                Napiwki[0],
                                Stawki[0],
                                Posady[0],
                                Opisy[0]
                            )
                        val zapisy = arrayListOf<Zapis>(z1)
                        for (i in 1..Daty.count() - 1) {
                            var r = Zapis(
                                Id[i],
                                Daty[i],
                                Godziny[i],
                                Napiwki[i],
                                Stawki[i],
                                Posady[i],
                                Opisy[i]
                            )
                            zapisy.add(r)
                        }

                        try {
                            fileWriter = FileWriter("customer.csv")

                            fileWriter.append(CSV_HEADER)
                            fileWriter.append('\n')

                            for (z in zapisy) {
                                fileWriter.append(z.id)
                                fileWriter.append('|')
                                fileWriter.append(z.data)
                                fileWriter.append('|')
                                fileWriter.append(z.godziny)
                                fileWriter.append('|')
                                fileWriter.append(z.napiwek)
                                fileWriter.append('|')
                                fileWriter.append(z.stawka)
                                fileWriter.append('|')
                                fileWriter.append(z.posada)
                                fileWriter.append('|')
                                fileWriter.append(z.opis)
                                fileWriter.append('|')
                            }

                            println("Write CSV successfully!")
                        } catch (e: Exception) {
                            println("Writing CSV error!")
                            e.printStackTrace()
                        } finally {
                            try {
                                fileWriter!!.flush()
                                fileWriter.close()
                            } catch (e: IOException) {
                                println("Flushing/closing error!")
                                e.printStackTrace()
                            }
                        }


                    } catch (e: Exception) {

                        Toast.makeText(
                            applicationContext,
                            "Baza danych jest pusta, dodaj dane aby móc utworzyć plik!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            catch(e: Exception){

                Toast.makeText(applicationContext,"Baza danych jest pusta, dodaj dane aby móc utworzyć plik!", Toast.LENGTH_LONG).show()
            }


    }
}
