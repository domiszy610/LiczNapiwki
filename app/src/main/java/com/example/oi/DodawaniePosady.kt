package com.example.oi

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.DatabaseErrorHandler
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dodawanie_posady.*
import java.lang.ArithmeticException

class DodawaniePosady : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodawanie_posady)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase



        val saveinfoToast = Toast.makeText(applicationContext,"Posada zapisana", Toast.LENGTH_SHORT)

        bDodPosade.setOnClickListener {
            val nazwa_posady : String = etposada.text.toString()
            val st : String = etstawka.text.toString()

            val stawka: Double =try{ st.toDouble() //NIE DZIALA WYLAPYWANIE WYJATKOW!!!!!!!!!!




            }
            catch (e: NumberFormatException){
                Toast.makeText(applicationContext,"Proszę podać poprawną wartość stawki!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener


            }

            if(stawka <0){
                throw ArithmeticException("Wartość stawki nie może przyjować wartości ujemnej!")
                return@setOnClickListener
            }


            //ZABEZPIECZYĆ PRZED PODANIEM ZŁYCH DANYCH

            val aktywnosc = true

            val value = ContentValues()
            value.put("NazwaPosady", nazwa_posady)
            value.put("Stawka", st)
            value.put("Aktywnosc", aktywnosc)








            if(nazwa_posady == null) {
                Toast.makeText(applicationContext,"Podaj nazwę posady!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (stawka == null){
                Toast.makeText(applicationContext,"Podaj stawkę godzinową!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                db.insertOrThrow(Table2Info.TABLE_NAME, null, value)
                saveinfoToast.show()
            }




        }





    }


}
