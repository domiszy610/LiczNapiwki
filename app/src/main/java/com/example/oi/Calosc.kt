package com.example.oi

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_calosc.*
import kotlinx.android.synthetic.main.row.view.*
import java.text.SimpleDateFormat
import java.util.*

class Calosc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calosc)
        var data = ""
        var posada = ""
        var godziny = ""
        var napiwek = ""
        var stawka = ""





        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val cursor1: Cursor = db.rawQuery("SELECT * FROM ${Table1Info.TABLE_NAME}", null)




        if (cursor1 != null) {
            cursor1.moveToFirst()
            data = cursor1.getString(3)
            godziny = cursor1.getString(2)
            napiwek = cursor1.getString(1)
            //id_posady = cursor1.getString(4)




            for (i in 1..cursor1.getCount() - 1) {
                cursor1.moveToNext()



                data += " ${cursor1.getString(3)}"
                godziny += " ${cursor1.getString(2)}"
                napiwek += " ${cursor1.getString(1)}"
                //id_posady += " ${cursor1.getString(4)}"


            }

        } else {
            Toast.makeText(
                applicationContext,
                "Nic nie ma w bazie danych w polu posada!",
                Toast.LENGTH_SHORT
            ).show()
        }
        val cursor2: Cursor = db.rawQuery("SELECT ${Table1Info.TABLE_COLUMN_ID_POSADA}, ${Table2Info.TABLE_COLUMN_NAZWA_POSADY}, ${Table2Info.TABLE_COLUMN_STAWKA}   FROM ${Table1Info.TABLE_NAME} " +
                "INNER JOIN ${Table2Info.TABLE_NAME} ON ${Table1Info.TABLE_COLUMN_ID_POSADA} = ${Table2Info.TABLE_NAME}.${BaseColumns._ID} ", null)
        //var ID_posad = id_posady.split(" ").toTypedArray()
        if (cursor2 != null) {
            cursor2.moveToFirst()

            posada = "${cursor2.getString(1)}"
            stawka = "${cursor2.getString(2)}"





            for (i in 1..cursor2.getCount() - 1) {
                cursor2.moveToNext()
                posada += " ${cursor2.getString(1)}"
                stawka += " ${cursor2.getString(2)}"






            }

        } else {
            Toast.makeText(
                applicationContext,
                "Nic nie ma w bazie danych w polu posada!",
                Toast.LENGTH_SHORT
            ).show()
        }



//region
//        if (cursor2 != null) {
//            cursor2.moveToFirst()
//            for (i in 0..ID_posad.count()) {
//                if (ID_posad[i] == cursor2.getString(0)) {
//                    posada = " ${cursor2.getString(1)}"
//
//
//                }
//            }
//            for (j in 1..cursor2.getCount())
//            {
//                cursor2.moveToNext()
//                for (i in 1..ID_posad.count()-1) {
//
//                    if (ID_posad[i] == cursor2.getString(0)) {
//                        posada += " ${cursor2.getString(1)}"
//
//                    }
//
//                }
//
//            }
//
//
//
//        }
//        else{
//            Toast.makeText(applicationContext,"Nic nie ma w bazie danych w polu posada!", Toast.LENGTH_SHORT).show()
//        }
//endregion
        var Daty = data.split(" ").toTypedArray()
        var Godziny = godziny.split(" ").toTypedArray()
        var Napiwki = napiwek.split(" ").toTypedArray()
        var Stawki = stawka.split(" ").toTypedArray()
        var Posady = posada.split(" ").toTypedArray()









        var r1 = Rekord(Daty[0], Posady[0], Godziny[0], Napiwki[0])
        var Rows= arrayListOf<Rekord>(r1)





        for (i in 1..Daty.count()-1)
        {
            var r = Rekord(Daty[i], Posady[i], Godziny[i], Napiwki[i])
            Rows.add(r)
        }


        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(RekordItem(Rows[0]))


        for (i in 1..Rows.count()-1)
        {
            adapter.add(RekordItem(Rows[i]))

        }

        recycler_calosc.adapter = adapter



        var Stawki_Obl = arrayListOf<Double>(Stawki[0].toDouble())
        var Godziny_Obl =arrayListOf<Double>(Godziny[0].toDouble())
        var Napiwki_Obl = arrayListOf<Double>(Napiwki[0].toDouble())

        for (i in 1..Stawki_Obl.count()-1){
            Stawki_Obl.add(Stawki[i].toDouble())
            Godziny_Obl.add(Godziny[i].toDouble())
            Napiwki_Obl.add(Napiwki[i].toDouble())
        }

        var Suma:Double = (Stawki_Obl[0]*Godziny_Obl[0])
        Suma+=Napiwki_Obl[0]

        for(i in 1..Stawki_Obl.count()-1){
            Suma+=(Stawki_Obl[i]*Godziny_Obl[i])
            Suma+=Napiwki_Obl[i]

        }
        var SumaX = Suma.toString()
        tv_suma.text ="Suma: "+ SumaX





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
