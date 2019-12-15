package com.example.oi

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull
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
        val db = dbHelper.writableDatabase


        b4.setOnClickListener {
            val intent = Intent(this, DodawaniePosady::class.java)
            startActivity(intent)

        }


        val cursor: Cursor? = db.rawQuery("SELECT * FROM ${Table1Info.TABLE_NAME}", null)
        val cursor2: Cursor?= db.rawQuery("SELECT * FROM ${Table2Info.TABLE_NAME}", null)
        if (cursor != null) {
            if (cursor2 != null) {
                if(cursor.count==-1|| cursor2.count==-1  ){
                    b2.isEnabled = false
                    b2.isClickable = false

                    b3.isEnabled =false
                    b3.isClickable=false


                        if(cursor2.count==-1 ) {
                            b1.isClickable = false
                            b1.isEnabled = false


                        }


                } else{
                    b1.isClickable = true
                    b1.isEnabled = true

                    b2.isEnabled = true
                    b2.isClickable = true

                    b3.isEnabled =true
                    b3.isClickable=true

                    b1.setOnClickListener {
                        val intent = Intent(this, dodajZarobek::class.java)

                        startActivity(intent)
                    }

                    b2.setOnClickListener {
                        val intent = Intent(this, ZobaczRejestr::class.java)

                        startActivity(intent)
                    }
                    b3.setOnClickListener {
                        val intent = Intent(this, ImportujDaneDoPliku::class.java)

                        startActivity(intent)

                    }
                }
            }
        }



//region
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
        //endregion


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
//        if(cursor2!=null) {
//
//            b1.setOnClickListener {
//                val intent = Intent(this, dodajZarobek::class.java)
//
//                startActivity(intent)
//            }
//
//            if (cursor2 != null && cursor != null) {
//
//
//                b2.setOnClickListener {
//                    val intent = Intent(this, ZobaczRejestr::class.java)
//
//                    startActivity(intent)
//                }
//
//
//
//                b3.setOnClickListener {
//                    val intent = Intent(this, ImportujDaneDoPliku::class.java)
//
//                    startActivity(intent)
//
//                }
//            }
//        }



















    }





    }


