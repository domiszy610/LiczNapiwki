package com.example.oi

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Miesiac : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_miesiac)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val cursor1: Cursor = db.rawQuery("SELECT * FROM ${Table1Info.TABLE_NAME}", null)

        val cursor2: Cursor = db.rawQuery("SELECT * FROM ${Table2Info.TABLE_NAME}", null)

        val cursor3: Cursor = db.rawQuery("SELECT * FROM ${Table3Info.TABLE_NAME}", null)
    }
}
