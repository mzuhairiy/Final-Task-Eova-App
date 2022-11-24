package com.app.myapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.myapp.databinding.ActivityRumahsakitBinding
import com.app.myapp.databinding.ListItemBinding
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.log


open class RumahsakitActivity : AppCompatActivity() {
    val TAG ="RumahsakitActivityTAG";
    lateinit var requestQueue: RequestQueue
    lateinit var list : ArrayList<ProvincesModel>
    lateinit var binding :ActivityRumahsakitBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRumahsakitBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(this)
        list = ArrayList()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        getData()

//        Log.d("BEMDOL debug", "masuk code ini")
    }

    private fun getData() {
        val progressDialog = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        doAPI(DoApi.GET("rs.json", object  : DoApi.Listener{
            override fun onSuccess(data: String?) {
                progressDialog.dismiss()
                list.clear()
              //  Log.d(TAG, "onSuccess: "+data)
                val jsonArray = JSONArray(data)
                for ( i in 0 until jsonArray.length()){
                    val mJsonObjectProperty  = jsonArray.getJSONObject(i)

                    val wilayah = mJsonObjectProperty.getString("wilayah")
                    val w = wilayah.split(", ")
                    try {

                        val provinsi = w[1]
                        Log.d(TAG, "onSuccess: "+ provinsi )
                        if (!checkAvai(provinsi)) {
                        list.add(ProvincesModel (mJsonObjectProperty.getString("kode_rs"),
                            provinsi))
                    }
                    }catch (i : IndexOutOfBoundsException){
                        Log.d(TAG, "onSuccess: "+i.message)
                    }


                }

                binding.recyclerView.adapter = RvAdapter(list)
            }

            override fun onFail(data: String?) {
                progressDialog.dismiss()
                Log.d(TAG, "onFail: "+data)
            }
        }))
    }

    private fun checkAvai(wilayah: String): Boolean {
        for ( l in list){
            if (l.name == wilayah){
                return true
            }
        }
        return false
    }

    inner class RvAdapter(private val list : ArrayList<ProvincesModel>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
             inner class ViewHolder( val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return ViewHolder(binding)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val d  = list[position]
            with(holder){

                binding.textView.text = d.name
                binding.button.setOnClickListener {
                    val intent = Intent(this@RumahsakitActivity, Detailrs::class.java)
                    intent.putExtra("nama", d.name)
                    intent.putExtra("id", d.id)
                    startActivity(intent)
                }
            }

        }

        override fun getItemCount(): Int {
             return list.size
        }

    }



    data class ProvincesModel(val id:String,val name:String  )

      fun doAPI(stringRequest: StringRequest) {
        stringRequest.tag = this.javaClass.simpleName
        requestQueue.add(stringRequest)
    }

}