package com.example.oi

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_zobacz_rejestr.*


class ZobaczRejestr : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zobacz_rejestr)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val cursor1: Cursor = db.rawQuery("SELECT * FROM ${Table1Info.TABLE_NAME}", null)

        val cursor2: Cursor = db.rawQuery("SELECT * FROM ${Table2Info.TABLE_NAME}", null)

        val cursor3: Cursor = db.rawQuery("SELECT * FROM ${Table3Info.TABLE_NAME}", null)

        menu.setOnClickListener {
            var popup = PopupMenu(this, menu)
            popup.inflate(R.menu.rejestr_menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.menu_item_caly -> {
                        val intent = Intent(this, Calosc::class.java)
                        startActivity(intent)

                    }


                    R.id.menu_item_miesieczny -> {
                        val intent = Intent(this, Miesiac::class.java)
                        startActivity(intent)


                    }
                    R.id.menu_item_dzien -> {
                        val intent = Intent(this, dzien::class.java)
                        startActivity(intent)

                    }
                }
                true
            })
            popup.show()


            }
        }



    }

