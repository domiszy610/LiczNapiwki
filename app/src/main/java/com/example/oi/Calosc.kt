package com.example.oi

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_calosc.*
import kotlinx.android.synthetic.main.row.view.*

class Calosc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calosc)
        var data = ""
        var posada = ""
        var godziny = ""
        var napiwek = ""
        var id_posady = ""


        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val cursor1: Cursor = db.rawQuery("SELECT * FROM ${Table1Info.TABLE_NAME}", null)

        val cursor2: Cursor = db.rawQuery("SELECT * FROM ${Table2Info.TABLE_NAME}", null)


        if (cursor1 != null) {
            cursor1.moveToFirst()
            data = cursor1.getString(3)
            godziny = cursor1.getString(2)
            napiwek = cursor1.getString(1)
            id_posady = cursor1.getString(4)




            for (i in 1..cursor1.getCount() - 1) {
                cursor1.moveToNext()



                data += " ${cursor1.getString(3)}"
                godziny += " ${cursor1.getString(2)}"
                napiwek += " ${cursor1.getString(1)}"
                id_posady += " ${cursor1.getString(4)}"


            }

        } else {
            Toast.makeText(
                applicationContext,
                "Nic nie ma w bazie danych w polu posada!",
                Toast.LENGTH_SHORT
            ).show()
        }

        var ID_posad = id_posady.split(" ").toTypedArray()


        if (cursor2 != null) {
            cursor2.moveToFirst()
            for (i in 0..ID_posad.count()) {
                if (ID_posad[i] == cursor2.getString(0)) {
                    posada = " ${cursor2.getString(1)}"


                }
            }
            for (j in 1..cursor2.getCount())
            {
                cursor2.moveToNext()
                for (i in 1..ID_posad.count()-1) {

                    if (ID_posad[i] == cursor2.getString(0)) {
                        posada += " ${cursor2.getString(1)}"

                    }

                }

            }



        }
        else{
            Toast.makeText(applicationContext,"Nic nie ma w bazie danych w polu posada!", Toast.LENGTH_SHORT).show()
        }

        var Daty = data.split(" ").toTypedArray()
        var Godziny = godziny.split(" ").toTypedArray()
        var Napiwki = napiwek.split(" ").toTypedArray()
        var Posady = posada.split(" ").toTypedArray()
        lateinit var Rows : ArrayList<Rekord>

        for (i in 0..Daty.count())
        {
            var r = Rekord(Daty[i], Posady[i], Godziny[i], Napiwki[i])
            Rows?.plusAssign(r)

        }


        val adapter = GroupAdapter<GroupieViewHolder>()

        for (i in 0..Rows.count())
        {
            adapter.add(RekordItem(Rows[i]))

        }


    }

}
class RekordItem(val rekord: Rekord) : Item<GroupieViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.row

    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.data_tx.text = rekord.data
        viewHolder.itemView.posada_tx.text = rekord.posada
        viewHolder.itemView.godziny_tx.text = rekord.godziny
        viewHolder.itemView.napiwek_tx.text = rekord.napiwek



    }
}
