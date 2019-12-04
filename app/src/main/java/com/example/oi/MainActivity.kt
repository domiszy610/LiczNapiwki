package com.example.oi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar







        var b1 = findViewById(R.id.b1) as Button
        b1.setOnClickListener {
            val intent = Intent(this, dodajZarobek::class.java)
            startActivity(intent)
        }
        var b2 = findViewById(R.id.b2) as Button
        b2.setOnClickListener {
            val intent = Intent(this, ZobaczRejestr::class.java)
            startActivity(intent)
        }
        var b3 = findViewById(R.id.b3) as Button
        b3.setOnClickListener {
            val intent = Intent(this, ImportujDaneDoPliku::class.java)
            startActivity(intent)
        }
        var b4 = findViewById(R.id.b4) as Button
        b4.setOnClickListener {
            val intent = Intent(this, DodawaniePosady::class.java)
            startActivity(intent)
        }







    }





    }


