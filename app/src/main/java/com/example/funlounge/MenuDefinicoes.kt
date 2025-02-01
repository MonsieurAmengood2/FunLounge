package com.example.funlounge


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuDefinicoes: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_definicoes_utilizador)


        val EditarPerfilBtn: TextView = findViewById(R.id.EditarPerfilBtn)
        val SairBtn: TextView = findViewById(R.id.SairBtn)



    }
}