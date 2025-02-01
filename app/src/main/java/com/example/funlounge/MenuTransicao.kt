package com.example.funlounge


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuTransicao: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menutransicao)


        val JogarBtn: TextView = findViewById(R.id.JogarBtn)
        val StatsBtn: TextView = findViewById(R.id.StatsBtn)
        val DefinBtn: Button = findViewById(R.id.DefinBtn)

        JogarBtn.setOnClickListener {
            val intent = Intent(this, MenuDecisaoJogo::class.java)
            startActivity(intent)
        }

        DefinBtn.setOnClickListener {
            val intent = Intent(this, MenuDefinicoes::class.java)
            startActivity(intent)
        }

        StatsBtn.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }



    }
}