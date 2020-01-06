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


        menu.setOnClickListener {
            var popup = PopupMenu(this, menu)
            popup.inflate(R.menu.rejestr_menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.menu_item_caly -> {
                        try {
                        val intent = Intent(this, Calosc::class.java)

                            startActivity(intent)
                        }
                        catch (e: Exception) {

                            Toast.makeText(
                                applicationContext,
                                "Baza danych jest pusta, dodaj dane aby móc zobaczyć rejestr!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }


                    R.id.menu_item_miesieczny -> {
                        try {
                        val intent = Intent(this, Miesiac::class.java)

                            startActivity(intent)
                        }
                        catch (e: Exception) {

                            Toast.makeText(
                                applicationContext,
                                "Baza danych jest pusta, dodaj dane aby móc zobaczyć rejestr!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }



                    }
                    R.id.menu_item_dzien -> {
                        try {
                        val intent = Intent(this, dzien::class.java)

                            startActivity(intent)
                        }
                        catch (e: Exception) {

                            Toast.makeText(
                                applicationContext,
                                "Baza danych jest pusta, dodaj dane aby móc zobaczyć rejestr!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    }
                }
                true
            })
            popup.show()


            }
        }



    }

