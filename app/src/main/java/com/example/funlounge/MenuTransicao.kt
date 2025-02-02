package com.example.funlounge


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


//Menu que aparece após se fazer Login com sucesso
class MenuTransicao: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menutransicao)


        val jogarBtn: TextView = findViewById(R.id.JogarBtn)
        val statsBtn: TextView = findViewById(R.id.StatsBtn)
        val definBtn: Button = findViewById(R.id.DefinBtn)

        jogarBtn.setOnClickListener {
            val intent = Intent(this, MenuDecisaoJogo::class.java)
            startActivity(intent)
        }

        definBtn.setOnClickListener {
            val intent = Intent(this, MenuDefinicoes::class.java)
            startActivity(intent)
        }

        statsBtn.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }



    }
}