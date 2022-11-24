package com.app.myapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.myapp.databinding.ActivityDetailrsBinding
import com.app.myapp.databinding.ListRumahSakitBinding
import org.json.JSONArray
import org.json.JSONException



class Detailrs : AppCompatActivity() {

    val TAG ="DetailrsTAG";
    lateinit var requestQueue: RequestQueue
    lateinit var binding : ActivityDetailrsBinding;
    lateinit var list : ArrayList <RumahSakitModel>
    lateinit var kotas :  ArrayList <String>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailrsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(this)
        binding.textView.text = "Daftar Rumah Sakit di  ${intent.getStringExtra("nama")}"
        list = ArrayList();
        kotas = ArrayList()
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        getData()
        setMenuItem()
    }

    private fun setMenuItem() {
        val menu = PopupMenu(this,  binding.btnFilter)
        binding.btnFilter.setOnClickListener {
            menu.menu.clear()
            menu.menu.add("SEMUA")
            for (k in kotas){
                menu.menu.add(k)
            }
            menu.show();
        }
        menu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
            if (menuItem.title.equals("SEMUA")) {
                binding.recyclerView.adapter = RvAdapter(list)
            }else{
                val filterList :ArrayList<RumahSakitModel> = ArrayList()
                for (l in list){
                    if (l.wilayah.startsWith(menuItem.title)) {
                        filterList.add(l)
                    }
                }

                binding.recyclerView.adapter = RvAdapter(filterList)
            }


            true
        })
    }

    private fun getData() {
        val progressDialog = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        progressDialog.setCancelable(false)
        doAPI(DoApi.GET("rs.json", object  : DoApi.Listener{
            override fun onSuccess(data: String?) {
                progressDialog.dismiss()

                val jsonArray = JSONArray(data)
                for ( i in 0 until jsonArray.length()){
                    val mJsonObjectProperty  = jsonArray.getJSONObject(i)
                    val wilayah = mJsonObjectProperty.getString("wilayah")
                    try {
                        if (wilayah.endsWith(intent.getStringExtra("nama")!!)) {
                            val obj = mJsonObjectProperty.getJSONObject("lokasi")
                            val lat =obj.getDouble("lat")
                            val lon =obj.getDouble("lon")


                            list.add(
                                RumahSakitModel(
                                    mJsonObjectProperty.getString("kode_rs"),
                                    mJsonObjectProperty.getString("nama"),
                                    mJsonObjectProperty.getString("alamat"),
                                    mJsonObjectProperty.getString("telepon"),
                                    lat,lon,wilayah
                                )
                            )


                            val w = wilayah.split(", ")

                            val kota = w[0]
                            if (!checkingAvai(kota)){
                                kotas.add(kota)

                            }
                        }
                    }catch (j :JSONException ){
                        Log.d(TAG, "onSuccess: "+j.message)
                    }catch (i:IndexOutOfBoundsException){
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

    private fun checkingAvai(kota: String): Boolean {
        for (k in kotas){
            if (k.startsWith( kota)){
                return true
            }
        }
        return false
    }

    inner class RvAdapter(private val list : ArrayList<RumahSakitModel>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
        inner class ViewHolder( val binding: ListRumahSakitBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ListRumahSakitBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return ViewHolder(binding)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val d  = list[position]
            with(holder){

                binding.textViewName.text = "Nama : "+d.name
                binding.textViewAddress.text = "Alamat : "+ d.address
                binding.textViewTelpon.text = "Telepon : "+d.phone +" \n" +d.wilayah
                binding.button.setOnClickListener {

                  //  val geo = "geo:"+d.lat+","+d.lon
                    val geo =  "google.navigation:q=" + d.lat + "," + d.lon+"&dirflg=d"

                    val gmmIntentUri = Uri.parse(geo)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    mapIntent.resolveActivity(packageManager)?.let {
                        startActivity(mapIntent)
                    }

                }
            }

        }

        override fun getItemCount(): Int {
            return list.size
        }

    }
    data class RumahSakitModel(val id:String,val name:String, val address:String,
                               val phone:String?="", val lat:Double, val lon:Double,
                                val wilayah :String)

    fun doAPI(stringRequest: StringRequest) {
        stringRequest.tag = this.javaClass.simpleName
        requestQueue.add(stringRequest)
    }

}