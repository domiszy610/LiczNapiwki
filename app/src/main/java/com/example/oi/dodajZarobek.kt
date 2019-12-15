package com.example.oi

import android.content.ContentValues
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.*
import android.widget.AdapterView.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dodaj_zarobek.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import android.widget.AdapterView.OnItemSelectedListener as OnItemSelectedListener
fun convertLongToTime(time: Long): String{
    val date = Date(time)
    val format = SimpleDateFormat("dd.MM.yyyy")
    return format.format(date)}

class dodajZarobek : AppCompatActivity() {

    lateinit var option : Spinner
    lateinit var posada : String


/////////////////////////////////////////////////////////////////////////////////////////buClickValue = buClickValue.replace(",", ".") ZROBIć na WPROWADZANIE !!!!!!!!!!!!!!!!!!!!!


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_zarobek)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val A: Array<String>
        var a = ""
//        var id_posady = listOf<Int>()


        val cursor: Cursor = db.rawQuery("SELECT * FROM ${Table2Info.TABLE_NAME}", null)




        if (cursor != null) {
            cursor.moveToFirst()
            a = cursor.getString(1)


            //id_posady.add(cursor.getString(0))
            for (i in 1..cursor.getCount() - 1) {
                cursor.moveToNext()

//                id_posady.add(cursor.getInt(0))

                a += " ${cursor.getString(1)}"


            }

        } else {
            Toast.makeText(
                applicationContext,
                "Nic nie ma w bazie danych w polu posada!",
                Toast.LENGTH_SHORT
            ).show()
        }


        A = a.split(" ").toTypedArray()



        option = findViewById(R.id.spinner) as Spinner

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, A)
//
        option.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                posada = A[0]

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                posada = A.get(position)
            }


        }

        var data = ""


        var Kalendarz1 = findViewById<CalendarView>(R.id.Kalendarz)
        Kalendarz1?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if (month <= 8 && dayOfMonth <= 9) {
                data = "0" + dayOfMonth + ".0" + (month + 1) + "." + year
            } else if (month >= 9 && dayOfMonth <= 9) {
                data = "0" + dayOfMonth + "." + (month + 1) + "." + year
            } else if (month <= 8 && dayOfMonth >= 10) {
                data = "" + dayOfMonth + ".0" + (month + 1) + "." + year
            } else {
                data = "" + dayOfMonth + "." + (month + 1) + "." + year
            }






            bDodajZarobek.setOnClickListener {
                val nap: String = et_napiwek.text.toString()
                val lic: String = et_godziny.text.toString()
                var id_posady: Int = 0

                if (cursor != null) {
                    cursor.moveToFirst()
                    if (cursor.getString(1) == posada) {
                        id_posady = cursor.getInt(0)
                    }

                    //id_posady.add(cursor.getString(0))
                    for (i in 1..cursor.getCount() - 1) {
                        cursor.moveToNext()
                        if (cursor.getString(1) == posada) {
                            id_posady = cursor.getInt(0)
                        }
                    }

                }


                val napiwek: Double = try {
                    nap.toDouble()


                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        applicationContext,
                        "Proszę podać poprawną wartość napiwku!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener


                }
                val godziny: Double = try {
                    lic.toDouble()


                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        applicationContext,
                        "Proszę podać poprawną wartość godzin!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener


                }

                // ID POSADA I ID DZIENNIK
                //   Zapis do dziennika aktywnosci   Opis: data i godzina
                var op = (LocalDateTime.now()).toString()
                val value_tab3 = ContentValues()
                value_tab3.put("Opis", op)

                var id_aktywnosci = 0

                db.insertOrThrow(Table3Info.TABLE_NAME, null, value_tab3)

                val cursor3: Cursor = db.rawQuery("SELECT * FROM ${Table3Info.TABLE_NAME}", null)
                if (cursor3 != null) {
                    cursor3.moveToFirst()
                    if (cursor3.getString(1) == op) {
                        id_aktywnosci = cursor3.getInt(0)
                    }

                    for (i in 1..cursor3.getCount() - 1) {
                        cursor3.moveToNext()
                        if (cursor3.getString(1) == op) {
                            id_aktywnosci = cursor3.getInt(0)
                        }
                    }


                }


//             val data_b = Kalendarz1.getDate()
//            val data = convertLongToTime(data_b)


                val id_posady1 = id_posady.toString()
                val id_aktywnosci1 = id_aktywnosci.toString()
                val value_tab1 = ContentValues()
                value_tab1.put("Napiwek", nap)
                value_tab1.put("Godziny", lic)
                value_tab1.put("Data", data)
                value_tab1.put("PosadaID", id_posady1)
                value_tab1.put("DziennikAktywnosciID", id_aktywnosci1)



                if (id_aktywnosci == null) {
                    Toast.makeText(applicationContext, "BLAD XD!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if (id_posady == null) {
                    Toast.makeText(
                        applicationContext,
                        "Proszę uzupełnić pole posada!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (!nap.isNullOrEmpty() || !lic.isNullOrEmpty()) {

                    db.insertOrThrow(Table1Info.TABLE_NAME, null, value_tab1)
                    Toast.makeText(
                        applicationContext,
                        "Udało się dodać do bazy danych!",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Proszę uzupełnić wszystkie pola!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }


            }
        }


    }


    }
