package com.example.oi

import android.Manifest
import android.R.attr
import android.R.attr.data
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.BaseColumns
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_importuj_dane_do_pliku.*
import java.io.*
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private val CSV_HEADER = "Id,Data,Liczba godzin,Posada,Stawka,Napiwek,Opis z dziennika aktywnosci"

class ImportujDaneDoPliku : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_importuj_dane_do_pliku)
        var data_zap = ""

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted = current.format(formatter)

        data_zap = formatted

        var NazwaPliku = "Rejestr_"+data_zap+".txt"

        var id = ""
        var daty = ""
        var posada = ""
        var godziny = ""
        var napiwek = ""
        var stawka = ""
        var opis =""

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        var bEks = findViewById(R.id.bEks) as Button
            try {
                bEks.setOnClickListener {


                    try {

                        val cursor1: Cursor = db.rawQuery(
                            "SELECT ${Table1Info.TABLE_NAME}.${BaseColumns._ID}, ${Table1Info.TABLE_COLUMN_DATA}, ${Table1Info.TABLE_COLUMN_NAPIWEK}, ${Table2Info.TABLE_COLUMN_NAZWA_POSADY}, ${Table2Info.TABLE_COLUMN_STAWKA}, ${Table1Info.TABLE_COLUMN_GODZINY} FROM ${Table1Info.TABLE_NAME} " +
                                    "INNER JOIN ${Table2Info.TABLE_NAME} ON ${Table1Info.TABLE_COLUMN_ID_POSADA} = ${Table2Info.TABLE_NAME}.${BaseColumns._ID}",
                            null
                        )

                        val cursor2: Cursor = db.rawQuery(
                            "SELECT ${Table3Info.TABLE_COLUMN_OPIS}  FROM ${Table1Info.TABLE_NAME} " +
                                    "INNER JOIN ${Table3Info.TABLE_NAME} ON ${Table1Info.TABLE_COLUMN_ID_DZIENNIK_AKTYWNOSCI} = ${Table3Info.TABLE_NAME}.${BaseColumns._ID}",
                            null
                        )

                        if (cursor1 != null) {
                            cursor1.moveToFirst()
                            id = cursor1.getString(0)
                            daty = cursor1.getString(1)
                            posada = cursor1.getString(3)
                            godziny = cursor1.getString(5)
                            napiwek = cursor1.getString(2)
                            stawka = cursor1.getString(4)





                            for (i in 1..cursor1.getCount() - 1) {
                                cursor1.moveToNext()

                                id += " ${cursor1.getString(0)}"
                                daty+= " ${cursor1.getString(1)}"
                                posada += " ${cursor1.getString(3)}"
                                godziny += " ${cursor1.getString(5)}"
                                napiwek += " ${cursor1.getString(2)}"
                                stawka += " ${cursor1.getString(4)}"


                            }

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Nic nie ma w bazie danych w polu posada!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (cursor2 != null) {
                            cursor2.moveToFirst()
                            opis = cursor2.getString(0)






                            for (i in 1..cursor2.getCount() - 1) {
                                cursor2.moveToNext()

                                opis += " ${cursor2.getString(0)}"


                            }

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Nic nie ma w bazie danych w polu posada!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        var Id = id.split(" ").toTypedArray()
                        var Daty = daty.split(" ").toTypedArray()
                        var Godziny = godziny.split(" ").toTypedArray()
                        var Napiwki = napiwek.split(" ").toTypedArray()
                        var Stawki = stawka.split(" ").toTypedArray()
                        var Posady = posada.split(" ").toTypedArray()
                        var Opisy = opis.split(" ").toTypedArray()

                        val CSV_HEADER ="Id | Data | liczba godzin | Posada | Stawka |  Napiwek | Opis z dziennika aktywnosci"
                        var data = StringBuilder()
                        data.append(CSV_HEADER)
                        var z1 =
                            Zapis(
                                Id[0],
                                Daty[0],
                                Godziny[0],
                                Napiwki[0],
                                Stawki[0],
                                Posady[0],
                                Opisy[0]
                            )
                        val zapisy = arrayListOf<Zapis>(z1)
                        for (i in 1..Daty.count() - 1) {
                            var r = Zapis(
                                Id[i],
                                Daty[i],
                                Godziny[i],
                                Napiwki[i],
                                Stawki[i],
                                Posady[i],
                                Opisy[i]
                            )
                            zapisy.add(r)
                        }

                            for (z in zapisy) {
                                data.append(("\n " + z.id + " | " + z.data + " | " + z.godziny + " | " + z.napiwek + " | " + z.posada + " | " + z.opis))
//                            data.append('|')
//                            data.append(z.data)
//                            data.append('|')
//                            data.append(z.godziny)
//                            data.append('|')
//                            data.append(z.napiwek)
//                            data.append('|')
//                            data.append(z.stawka)
//                            data.append('|')
//                            data.append(z.posada)
//                            data.append('|')
//                            data.append(z.opis)
//                            data.append('|')
                            }

                        var DataFinal = data.toString()










                        try {



                            var MY_PERMISSIONS_REQUEST_STORAGE = 0
                            var MY_PERMISSIONS_REQUEST_STORAGE2 = 0
                            if ((ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED)) {
                                ActivityCompat.requestPermissions(
                                    this,
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                    MY_PERMISSIONS_REQUEST_STORAGE
                                )

                            }
                            if(ContextCompat.checkSelfPermission(this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                    PackageManager.PERMISSION_GRANTED)
                            {ActivityCompat.requestPermissions(
                                    this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_STORAGE2)
                            }
                            else {
//                                val sendIntent = Intent()
//                                var path = writeFile(this,NazwaPliku, DataFinal)
//                                var dir = path+"/${NazwaPliku}"
//                                sendIntent.action = Intent.ACTION_SENDTO
//                                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(dir))
//                                sendIntent.type = "text/csv"
//                                startActivity(Intent.createChooser(sendIntent, "SHARE"))
//                                Toast.makeText(applicationContext,"Plik został zapisany!", Toast.LENGTH_LONG).show()



//                                if(isExternalStorageWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                                    val directory = "MyFileStorage"
//                                    val file = File("text.txt")
//                                    val fileW : FileWriter = FileWriter(file.path)
//                                    try{
//                                        fileW.write("AAAAA")
//                                        fileW.write("F")
//                                    } catch (e: Exception ) {
//                                    e.printStackTrace()
//                                }finally {
//                                    try {
//                                        if (fileW != null) {
//                                            fileW.flush()
//                                            fileW.close()
//                                        }
//                                    } catch (e: IOException ) {
//                                        e.printStackTrace()
//                                    }
//                                }



//                                    file.createNewFile()
//                                    file.writeText("My Text", Charsets.UTF_8)


//
//                                    val fileURI = FileProvider.getUriForFile(
//                                        this,
//                                        "$packageName.fileprovider",
//                                        file
//
//                                    )
                                val s = "Rejestr na stan z dnia: "+data_zap

                                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                                        putExtra(Intent.EXTRA_SUBJECT, s )
                                        putExtra(Intent.EXTRA_TEXT, DataFinal)
//                                        putExtra(Intent.EXTRA_STREAM, fileURI)
                                    }
//                                    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                   emailIntent.type = "text/html"
                                   startActivity(emailIntent)

                            }



                        }
                        catch(e: Exception){
                            e.printStackTrace()
                            Toast.makeText(
                                applicationContext,
                                "Nie udało się zapisać pliku!",
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    } catch (e: Exception) {

                        Toast.makeText(
                            applicationContext,
                            "Baza danych jest pusta, dodaj dane aby móc przesłać dane!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            catch(e: Exception){

                Toast.makeText(applicationContext,"Baza danych jest pusta, dodaj dane aby móc przesłać dane!", Toast.LENGTH_LONG).show()
            }

        buDROP.setOnClickListener {
            try {
                db?.execSQL("DELETE FROM ${Table1Info.TABLE_NAME}")
                db?.execSQL("DELETE FROM ${Table2Info.TABLE_NAME}")
                db?.execSQL("DELETE FROM ${Table3Info.TABLE_NAME}")
                Toast.makeText(
                    applicationContext,
                    "Baza danych została usunięta!",
                    Toast.LENGTH_LONG
                ).show()
            }
            catch(e: Exception){
                Toast.makeText(
                    applicationContext,
                    "Baza danych jest już pusta!",
                    Toast.LENGTH_LONG
                ).show()
            }

        }


    }

    private fun  isExternalStorageWritable(): Boolean{
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.i("State", "Wriatable")
            return true
        }
        else{
            return false
        }


    }
    private fun  isExternalStorageReadable(): Boolean{
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState()) ){
            Log.i("State", "Readable")
            return true
        }
        else{
            return false
        }


    }
    public fun writeFile(context: Context, s:String, n :String): String{
        var path :String =""
        val filepath = "MyFileStorage"
//region
//        var myExternalFile: File
//        if(isExternalStorageWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//            val filepath = "MyFileStorage"
//            path = getExternalFilesDir(filepath).toString()
//            myExternalFile = File(getExternalFilesDir(filepath),s)
//            myExternalFile.writeText(n)
////            File(getExternalFilesDir(filepath).toString()+"/${s}").writeText(n)
//        try {
//          val fileOutPutStream = FileOutputStream(myExternalFile)
//            fileOutPutStream.write(n.toByteArray())
//
//               myExternalFile.writeText(n)
//
//
//
//
//               fileOutPutStream.close()
//               Toast.makeText(applicationContext,"ZAPISUJĘ DO PLIKU", Toast.LENGTH_LONG).show()
//               } catch (e: IOException) {
//               Toast.makeText(applicationContext,"NIE ZAPISUJĘ NIC DO PLIKU", Toast.LENGTH_LONG).show()
//
//        }
//        }
//endregion
        val sd_main = File(Environment.getExternalStorageDirectory().toString()+"/"+filepath)
        var success = true
        path = Environment.getExternalStorageDirectory().toString()+"/"+filepath
            if (!sd_main.exists()) {
            success = sd_main.mkdir()
        }
        if (success) {
            val sd = File(s)

            if (!sd.exists()) {
                success = sd.mkdir()
            }
            if (success) {
                // directory exists or already created
                val dest = File(sd, s)
                try {
                    // response is the data written to file
                    PrintWriter(dest).use { out -> out.println(n) }
                } catch (e: Exception) {
                    // handle the exception
                }

            } else {
                // directory creation is not successful
            }
        }

        return path

    }
    public fun checkPermission(permission: String): Boolean{
        var check :Int = ContextCompat.checkSelfPermission(this, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }
}
