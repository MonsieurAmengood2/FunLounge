package com.example.funlounge

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

//Menu inicial que permite iniciar sessão ou registrar uma conta
class MenuInicial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menuinicial)


        val signInBtn: TextView = findViewById(R.id.signInBtn)
        val signUpBtn: TextView = findViewById(R.id.signUpBtn)


        signInBtn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


    }
}