package com.app.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class Quest1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest1)

        val buttonYes = findViewById<Button>(R.id.buttonYes1)
        val buttonNo = findViewById<Button>(R.id.buttonNo1)

        intent = Intent(applicationContext, Quest2::class.java)
        buttonYes.setOnClickListener {
            intent.putExtra("answer_val", "1")
            startActivity(intent)
        }
        buttonNo.setOnClickListener {
            intent.putExtra("answer_val", "0")
            startActivity(intent)
        }
    }
}
