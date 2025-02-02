package com.example.funlounge

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//Menu inicial que permite iniciar sessão ou registrar uma conta
class MenuInicial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menuinicial)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainMenu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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