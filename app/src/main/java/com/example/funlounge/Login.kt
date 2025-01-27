package com.example.funlounge

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val signInBtn: TextView = findViewById(R.id.signInBtn)


        signInBtn.setOnClickListener {
            val intent = Intent(this, AdicionarJogadores::class.java)
            startActivity(intent)
        }


    }
}