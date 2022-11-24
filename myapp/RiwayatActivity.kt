package com.app.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import okhttp3.*




class RiwayatActivity : AppCompatActivity() {
    val client = OkHttpClient()
    var hasil: String = "test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

//        val answerVal = intent.extras?.getString("answer_val")
//        hasil = answerVal.toString()
//        Log.d("hasilnya", answerVal.toString())
        val myWebView: WebView = findViewById(R.id.webviewrw)
        myWebView.loadUrl("http://134.209.109.247:5000/riwayat")
    }

    fun gotors(view: View) {
        intent = Intent(applicationContext, RumahsakitActivity::class.java)
        startActivity(intent);
    }
}