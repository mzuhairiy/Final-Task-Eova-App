package com.app.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        val logout = findViewById<Button>(R.id.logout_btn)

        logout.setOnClickListener {
            auth.signOut()
          intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }


    }
    fun gotoquest1 (view: View) {
        intent = Intent(applicationContext, Quest1::class.java)
        startActivity(intent)
    }
    fun gotoriwayat (view: View) {
        intent = Intent(applicationContext, RiwayatActivity::class.java)
        startActivity(intent)
    }
    fun gotors (view: View) {
        intent = Intent(applicationContext, RumahsakitActivity::class.java)
        startActivity(intent)
    }
//    fun gotodashboard (view: View){
//        intent = Intent(applicationContext, DashboardActivity::class.java)
//        startActivity(intent)
//    }
}