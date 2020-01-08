package com.example.oi

import android.R.attr
import android.R.attr.data
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


private val CSV_HEADER = "Id,Data,Liczba godzin,Posada,Stawka,Napiwek,Opis z dziennika aktywnosci"

class ImportujDaneDoPliku : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_importuj_dane_do_pliku)

        var id = ""
        var daty = ""
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
                            daty = cursor1.getString(1)
                            posada = cursor1.getString(3)
                            godziny = cursor1.getString(5)
                            napiwek = cursor1.getString(2)
                            stawka = cursor1.getString(4)





                            for (i in 1..cursor1.getCount() - 1) {
                                cursor1.moveToNext()

                                id += " ${cursor1.getString(0)}"
                                daty+= " ${cursor1.getString(1)}"
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
                        var Daty = daty.split(" ").toTypedArray()
                        var Godziny = godziny.split(" ").toTypedArray()
                        var Napiwki = napiwek.split(" ").toTypedArray()
                        var Stawki = stawka.split(" ").toTypedArray()
                        var Posady = posada.split(" ").toTypedArray()
                        var Opisy = opis.split(" ").toTypedArray()


                        val plik = StringBuilder()
                        plik.append("Id,Data,iczba godzin,Posada,Stawka, Napiwek,Opis z dziennika aktywnosci")
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
                        for (z in zapisy) {
                            plik.append("\n" + z.id + "," + z.data + "," + z.godziny + "," + z.napiwek + "," +  z.posada+ "," + z.opis )
//                            data.append('|')
//                            data.append(z.data)
//                            data.append('|')
//                            data.append(z.godziny)
//                            data.append('|')
//                            data.append(z.napiwek)
//                            data.append('|')
//                            data.append(z.stawka)
//                            data.append('|')
//                            data.append(z.posada)
//                            data.append('|')
//                            data.append(z.opis)
//                            data.append('|')
                        }

                        try {
                            val out: FileOutputStream = openFileOutput("data.csv", Context.MODE_PRIVATE)
                            out.write(plik.toString().toByteArray()) //CHYBA TU BLAD
                            out.close()




                            var context :Context = getApplicationContext()
                            var filelocation :File  = File(getFilesDir(), "data.csv")
                            var path :Uri = FileProvider.getUriForFile(context, "com.example.oi.fileprovider", filelocation)
                            var fileIntent : Intent  = Intent(Intent.ACTION_SEND)
                            fileIntent.setType("text/csv")
                            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data")
                            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            fileIntent.putExtra(Intent.EXTRA_STREAM, path)
                            startActivity(Intent.createChooser(fileIntent, "Send mail"))


                            Toast.makeText(applicationContext,"Plik został zapisany!", Toast.LENGTH_LONG).show()
                        }
                        catch(e: Exception){
                            e.printStackTrace()
                            Toast.makeText(
                                applicationContext,
                                "Nie udało się zapisać pliku!",
                                Toast.LENGTH_LONG
                            ).show()
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
