package com.app.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.app.myapp.databinding.ActivityRegisBinding
import com.google.firebase.auth.FirebaseAuth

class RegisActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvLogin.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonRegis.setOnClickListener {
            val email = binding.emailRegis.text.toString()
            val password = binding.passRegis.text.toString()

            if (email.isEmpty()){
                binding.emailRegis.error = "Email tidak boleh kosong."
                binding.emailRegis.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailRegis.error = "Email tidak sesuai."
                binding.emailRegis.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.passRegis.error = "Password tidak boleh kosong."
                binding.passRegis.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6 ){
                binding.passRegis.error = "Password minimal 6 karakter."
                binding.passRegis.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(email,password)
        }

    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Register Berhasil.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


//    fun gotologin (view: View) {
//        intent = Intent(applicationContext, LoginActivity::class.java)
//        startActivity(intent) }

}
