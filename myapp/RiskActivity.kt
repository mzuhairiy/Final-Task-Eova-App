package com.app.myapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.ArrayList

class RiskActivity : AppCompatActivity() {
//    lateinit var progress:ProgressBar
//    lateinit var listView_details: ListView
//    var arrayList_details:ArrayList<Model> = ArrayList();
    //OkHttpClient creates connection pool between client and server
    val client = OkHttpClient()
    var hasil:String = "test"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk)
//        listView_details = findViewById<ListView>(R.id.listView) as ListView


        val answerVal = intent.extras?.getString("answer_val")
        hasil = answerVal.toString()
//        run("http://10.0.2.2:5000/prediksi/"+answerVal.toString())

//        Log.d("hasilnya", answerVal.toString())
//        Log.d("link", "http://10.0.2.2:5000/prediksi/"+answerVal)


    }
//      fun gotors(view: View) {
//        val url = "http://10.0.2.2:5000/prediksi/"+hasil;
//        intent = Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(url));
//        startActivity(intent);
//        intent = Intent(applicationContext, RumahsakitActivity::class.java)
//        startActivity(intent)


    }
//    fun run(url: String) {
////        progress.visibility = View.VISIBLE
//
//        val request = Request.Builder()
//            .url(url)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//
//            override fun onFailure(call: Call, e: IOException) {
////                progress.visibility = View.GONE
//                Log.d("req","Request Fail")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                Log.d("resp","Respones")
//                var str_response = response.body()!!.string()
//                //creating json object
//                val json_contact:JSONObject = JSONObject(str_response)
//                //creating json array
//                var jsonarray_info:JSONArray= json_contact.getJSONArray("info")
//                var i:Int = 0
//                var size:Int = jsonarray_info.length()
//                var json_objectdetail:JSONObject=jsonarray_info.getJSONObject(0)
//                var diag = json_objectdetail.getString("status")
//                Log.d("diagnosa",diag)
//                arrayList_details= ArrayList();
//                for (i in 0.. size-1) {
//                    var json_objectdetail:JSONObject=jsonarray_info.getJSONObject(i)
//                    var model:Model= Model();
//                    model.status=json_objectdetail.getString("status")
//                    Log.d("diagnosa",model.status)
//
//                    arrayList_details.add(model)
//                }
//
//                runOnUiThread {
//                    //stuff that updates ui
//                    val obj_adapter : CustomAdapter
//                    obj_adapter = CustomAdapter(applicationContext,arrayList_details)
//                    listView_details.adapter=obj_adapter
//                }
//                progress.visibility = View.GONE
//            }
//        })
//    }
