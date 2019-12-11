package com.example.oi

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val b4 = findViewById(R.id.b4) as Button
        val b1 = findViewById(R.id.b1) as Button
        val b2 = findViewById(R.id.b2) as Button
        val b3 = findViewById(R.id.b3) as Button

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.readableDatabase


        b4.setOnClickListener {
            val intent = Intent(this, DodawaniePosady::class.java)
            startActivity(intent)

        }


        val cursor: Cursor?
        cursor = db.rawQuery("SELECT * FROM ${Table1Info.TABLE_NAME}", null)
        val cursor2: Cursor?
        cursor2 = db.rawQuery("SELECT * FROM ${Table2Info.TABLE_NAME}", null)


//        b1.setEnabled(cursor2!=null)
//
//        b3.setEnabled(cursor2!=null && cursor!=null)
//        b2.setEnabled(cursor2!=null && cursor!=null)

//        if(cursor != null && cursor2!= null)
//        {
//            b1.isEnabled= true
//            b2.isEnabled= true
//            b3.isEnabled= true
//            b1.setOnClickListener {
//                val intent = Intent(this, dodajZarobek::class.java)
//                startActivity(intent)
//            }
//
//            b2.setOnClickListener {
//                val intent = Intent(this, ZobaczRejestr::class.java)
//                startActivity(intent)
//            }
//
//            b3.setOnClickListener {
//                val intent = Intent(this, ImportujDaneDoPliku::class.java)
//                startActivity(intent)
//            }
//
//        }
//        else if(cursor2!=null){
//            b1.isEnabled= true
//            b1.setOnClickListener {
//                val intent = Intent(this, dodajZarobek::class.java)
//                startActivity(intent)
//            }
//
//
//        }

//region
    //if(cursor2!=null && cursor!=null){
//
//            b3.isClickable = true
//
//            b2.isEnabled = true
//            b2.isClickable = true
//
//            b1.isEnabled =true
//            b1.isClickable=true
//
//        }
//        if(cursor!=null){
//            b3.isEnabled = true
//            b3.isClickable = true
//
//            b2.isEnabled = true
//            b2.isClickable = true
//
//
//        }
//        else{
//
//
//        }
        //endregion


        b1.setOnClickListener {
                val intent = Intent(this, dodajZarobek::class.java)
            if(cursor2!=null) {
                startActivity(intent)
            }
        }


        b2.setOnClickListener {
            val intent = Intent(this, ZobaczRejestr::class.java)
            if(cursor2!=null && cursor!=null) {
                startActivity(intent)
            }

        }

        b3.setOnClickListener {
            val intent = Intent(this, ImportujDaneDoPliku::class.java)
            if(cursor2!=null && cursor!=null) {
                startActivity(intent)
            }
        }



















    }





    }


